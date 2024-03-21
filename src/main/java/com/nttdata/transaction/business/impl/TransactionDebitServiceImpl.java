package com.nttdata.transaction.business.impl;

import com.nttdata.transaction.api.request.TransactionAccountRequest;
import com.nttdata.transaction.api.request.TransactionDebitCardRequest;
import com.nttdata.transaction.api.request.TransactionTransferRequest;
import com.nttdata.transaction.builder.TransactionBuilder;
import com.nttdata.transaction.business.AccountService;
import com.nttdata.transaction.business.DebitCardService;
import com.nttdata.transaction.business.TransactionDebitService;
import com.nttdata.transaction.enums.AccountTypeEnum;
import com.nttdata.transaction.enums.TransactionAccountTypeEnum;
import com.nttdata.transaction.enums.TransactionTypeEnum;
import com.nttdata.transaction.model.Transaction;
import com.nttdata.transaction.model.account.Account;
import com.nttdata.transaction.model.debicard.AccountAssociated;
import com.nttdata.transaction.repository.TransactionRepository;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Class: TransactionDebitServiceImpl. <br/>
 * <b>Bootcamp NTTDATA</b><br/>
 *
 * @author NTTDATA
 * @version 1.0
 *   <u>Developed by</u>:
 *   <ul>
 *   <li>Developer Carlos</li>
 *   </ul>
 * @since 1.0
 */
@Service
public class TransactionDebitServiceImpl implements TransactionDebitService {

  @Autowired
  private TransactionRepository transactionRepository;

  @Autowired
  private AccountService accountService;

  @Autowired
  private DebitCardService debitCardService;

  @Override
  public Mono<Transaction> saveTransferTransaction(TransactionTransferRequest transactionRequest) {
    return this.validationAccountAvailableBalance(transactionRequest.getAccountNumberSource(),
        transactionRequest.getAmount())
      .flatMap(accountSource -> this.validationAccountLimitMovement(accountSource,
        TransactionTypeEnum.WIRE_TRANSFER.name()))
      .flatMap(
        accountSource -> accountService.findAccount(transactionRequest.getAccountNumberTarget())
          .flatMap(accountTarget -> this.calculationTransactionCommission(TransactionBuilder
              .toTransactionEntity(transactionRequest, accountSource), accountSource)
            .flatMap(transactionRepository::saveTransaction)
            .flatMap(transaction -> this.updateAccountAvailableBalance(accountSource,
                transaction.getAmount(), transaction.getTransactionType())
              .flatMap(account -> this.updateAccountAvailableBalance(accountTarget,
                transaction.getAmount(),
                TransactionAccountTypeEnum.DEPOSIT.name()))
              .thenReturn(transaction)
            )
          ));
  }

  @Override
  public Mono<Transaction> saveAccountTransaction(TransactionAccountRequest transactionRequest) {
    return this.validationAccountAvailableBalance(transactionRequest.getAccountNumberSource(),
        transactionRequest.getAmount())
      .flatMap(account -> this.validationAccountLimitMovement(account,
        transactionRequest.getType().name()))
      .flatMap(account -> this.calculationTransactionCommission(TransactionBuilder
          .toTransactionEntity(transactionRequest, account), account)
        .flatMap(transactionRepository::saveTransaction)
        .flatMap(transaction -> this.updateAccountAvailableBalance(account, transaction.getAmount(),
            transaction.getTransactionType())
          .thenReturn(transaction)
        ));
  }


  @Override
  public Mono<Transaction> saveDebitCardTransaction(
    TransactionDebitCardRequest transactionRequest) {
    return debitCardService.findDebitCard(transactionRequest.getCardNumber())
      .flatMap(debitCard -> this.validationAccountAvailableBalance(
          debitCard.getAccountsAssociated(),
          debitCard.getCardNumber(), transactionRequest.getAmount())
        .flatMap(account -> this.validationAccountLimitMovement(account,
          transactionRequest.getType().name()))
        .flatMap(account -> this.calculationTransactionCommission(TransactionBuilder
            .toTransactionEntity(transactionRequest, debitCard, account), account)
          .flatMap(transactionRepository::saveTransaction)
          .flatMap(
            transaction -> this.updateAccountAvailableBalance(account, transaction.getAmount(),
                transaction.getTransactionType())
              .thenReturn(transaction)
          ))
      );
  }

  private Mono<Transaction> calculationTransactionCommission(Transaction transaction,
    Account account) {

    return transactionRepository.countTransactions(account.getAccountNumber())
      .flatMap(counterTransactions -> {

        if (transaction.getTransactionType()
          .equals(TransactionAccountTypeEnum.MAINTENANCE_CHARGE.name())) {
          transaction.setCommission(account.getMaintenanceCommission());
        } else if (counterTransactions > account.getLimitFreeMovements()) {
          transaction.setCommission(account.getCommissionMovement());
        }
        return Mono.just(transaction);
      });
  }

  private Mono<Account> validationAccountLimitMovement(Account account, String transactionType) {

    return transactionRepository.countTransactions(account.getAccountNumber())
      .flatMap(counterTransactions -> {
        if (account.getMonthlyLimitMovement() > 0
          && counterTransactions > account.getMonthlyLimitMovement()) {
          return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST,
            "Maximum monthly transaction limit has been reached - Maximum limit :"
              .concat(account.getMonthlyLimitMovement().toString())));
        }

        if (transactionType.equals(AccountTypeEnum.TERM_DEPOSIT.name())
          && account.getSpecificDayMonthMovement() > 0
          && !account.getSpecificDayMonthMovement().equals(LocalDate.now().getDayOfMonth())) {
          return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST,
            "Transactions are permitted on : ".concat(
              account.getSpecificDayMonthMovement().toString())));
        }

        return Mono.just(account);
      });
  }

  private Flux<AccountAssociated> getAccounts(List<AccountAssociated> accountAssociatedList) {
    return Flux.fromStream(accountAssociatedList.stream()
      .sorted(Comparator.comparing(AccountAssociated::getAssociatedType)));
  }

  private Mono<Account> updateAccountAvailableBalance(Account account, BigDecimal
    transactionAmount,
    String transactionType) {

    account.setAvailableBalance(transactionType.equals(TransactionAccountTypeEnum.DEPOSIT.name())
      ? account.getAvailableBalance().add(transactionAmount)
      : account.getAvailableBalance().subtract(transactionAmount));

    return accountService.updateAccount(account);
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

  private Mono<Account> validationAccountAvailableBalance(BigInteger accountNumber,
    BigDecimal transactionAmount) {
    return accountService.findAccount(accountNumber)
      .filter(account -> account.getAvailableBalance().compareTo(transactionAmount) >= 0)
      .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST,
        "Cuenta bancaria sin saldo suficiente - accountNumber: " + accountNumber.toString())));
  }

}
