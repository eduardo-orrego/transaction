package com.nttdata.transaction.business.impl;

import com.nttdata.transaction.api.request.TransactionAccountRequest;
import com.nttdata.transaction.api.request.TransactionDebitCardRequest;
import com.nttdata.transaction.api.request.TransactionTransferRequest;
import com.nttdata.transaction.builder.TransactionBuilder;
import com.nttdata.transaction.business.AccountService;
import com.nttdata.transaction.business.DebitCardService;
import com.nttdata.transaction.business.TransactionDebitService;
import com.nttdata.transaction.enums.TransactionAccountTypeEnum;
import com.nttdata.transaction.model.Transaction;
import com.nttdata.transaction.model.account.Account;
import com.nttdata.transaction.model.debicard.AccountAssociated;
import com.nttdata.transaction.repository.TransactionRepository;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TransactionDebitServiceImpl implements TransactionDebitService {

    private final TransactionRepository transactionRepository;
    private final AccountService accountService;
    private final DebitCardService debitCardService;

    public TransactionDebitServiceImpl(TransactionRepository transactionRepository, AccountService accountService,
        DebitCardService debitCardService) {
        this.transactionRepository = transactionRepository;
        this.accountService = accountService;
        this.debitCardService = debitCardService;
    }

    @Override
    public Mono<Transaction> saveTransferTransaction(TransactionTransferRequest transactionRequest) {
        return this.validationAccountAvailableBalance(transactionRequest.getAccountNumberSource(),
                transactionRequest.getAmount())
            .flatMap(accountSource -> accountService.findAccount(transactionRequest.getAccountNumberTarget())
                .flatMap(accountTarget -> this.validationAccountLimitMovement(accountSource)
                    .map(accountValidated -> TransactionBuilder.toTransactionEntity(
                        transactionRequest, accountValidated, 0))
                    .flatMap(transactionRepository::saveTransaction)
                    .flatMap(transaction ->

                        this.updateAccountAvailableBalance(accountSource, transaction.getAmount(),
                                transaction.getTransactionType())
                            .flatMap(account ->
                                this.updateAccountAvailableBalance(accountTarget, transaction.getAmount(),
                                    transaction.getTransactionType())
                            )
                            .thenReturn(transaction)
                    )));
    }


    private Mono<Account> updateAccountAvailableBalance(Account account, BigDecimal transactionAmount,
        String transactionType) {

        account.setAvailableBalance(transactionType.equals(TransactionAccountTypeEnum.DEPOSIT.name())
            ? account.getAvailableBalance().add(transactionAmount)
            : account.getAvailableBalance().subtract(transactionAmount));

        return accountService.updateAccount(account);
    }

    @Override
    public Mono<Transaction> saveAccountTransaction(TransactionAccountRequest transactionRequest) {
        return this.validationAccountAvailableBalance(transactionRequest.getAccountNumberSource(),
                transactionRequest.getAmount())
            .flatMap(this::validationAccountLimitMovement)
            .flatMap(accountValidated -> transactionRepository.saveTransaction(TransactionBuilder.toTransactionEntity(
                    transactionRequest, accountValidated, 0))
                .flatMap(transaction ->
                    this.updateAccountAvailableBalance(accountValidated, transaction.getAmount(),
                            transaction.getTransactionType())
                        .thenReturn(transaction)
                ));
    }


    @Override
    public Mono<Transaction> saveDebitCardTransaction(TransactionDebitCardRequest transactionRequest) {
        return debitCardService.findDebitCard(transactionRequest.getCardNumber())
            .flatMap(debitCard -> this.validationAccountAvailableBalance(debitCard.getAccountsAssociated(),
                    debitCard.getCardNumber(), transactionRequest.getAmount())
                .flatMap(this::validationAccountLimitMovement)
                .flatMap(account -> transactionRepository.saveTransaction(
                        TransactionBuilder.toTransactionEntity(transactionRequest, debitCard, account))
                    .flatMap(transaction ->
                        this.updateAccountAvailableBalance(account, transaction.getAmount(),
                                transaction.getTransactionType())
                            .thenReturn(transaction)
                    )));
    }

    private Mono<Account> validationAccountAvailableBalance(List<AccountAssociated> accounts,
        BigInteger cardNumber, BigDecimal transactionAmount) {
        return this.getAccounts(accounts)
            .flatMap(accountAssociated -> accountService.findAccount(cardNumber))
            .filter(account -> account.getAvailableBalance().compareTo(transactionAmount) >= 0)
            .next()
            .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "Cuentas Bancarias sin saldo suficiente - cardNumber: " + cardNumber.toString())));

    }

    private Flux<AccountAssociated> getAccounts(List<AccountAssociated> accountAssociatedList) {
        return Flux.fromStream(accountAssociatedList.stream()
            .sorted(Comparator.comparing(AccountAssociated::getAssociatedType)));
    }

    private Mono<Account> validationAccountAvailableBalance(BigInteger accountNumber,
        BigDecimal transactionAmount) {
        return accountService.findAccount(accountNumber)
            .filter(account -> account.getAvailableBalance().compareTo(transactionAmount) >= 0)
            .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "Cuenta bancaria sin saldo suficiente - accountNumber: " + accountNumber.toString())));
    }


    private Mono<Account> validationAccountLimitMovement(Account account) {

        return transactionRepository.countTransactions(account.getAccountNumber())
            .flatMap(counterTransactions -> {
                if (account.getMonthlyLimitMovement() > 0
                    && counterTransactions > account.getMonthlyLimitMovement()) {
                    return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Maximum monthly transaction limit has been reached - Maximum limit :"
                            .concat(account.getMonthlyLimitMovement().toString())));
                }

                if (account.getSpecificDayMonthMovement() > 0
                    && !account.getSpecificDayMonthMovement().equals(LocalDate.now().getDayOfMonth())) {
                    return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Transactions are permitted on : ".concat(
                            account.getSpecificDayMonthMovement().toString())));
                }

                return Mono.just(account);
            });
    }

    private static BigDecimal getCommission(Account account, String transactionType, Integer counterTransactions) {

        if (counterTransactions > account.getLimitFreeMovements()) {
            if (transactionType.equals(TransactionAccountTypeEnum.DEPOSIT.name())
                || transactionType.equals(TransactionAccountTypeEnum.WITHDRAWAL.name())
                || transactionType.equals(TransactionAccountTypeEnum.WIRE_TRANSFER.name()))
                return account.getCommissionMovement();

            if (transactionType.equals(TransactionAccountTypeEnum.MAINTENANCE_CHARGE.name()))
                return account.getMaintenanceCommission();
        }

        return BigDecimal.valueOf(0.00);
    }
}
