package com.nttdata.transaction.business.impl;

import com.nttdata.transaction.api.request.TransactionRequest;
import com.nttdata.transaction.builder.TransactionBuilder;
import com.nttdata.transaction.business.AccountService;
import com.nttdata.transaction.business.CreditService;
import com.nttdata.transaction.business.TransactionService;
import com.nttdata.transaction.enums.CreditTypeEnum;
import com.nttdata.transaction.enums.TransactionTypeEnum;
import com.nttdata.transaction.model.TransactionEntity;
import com.nttdata.transaction.repository.TransactionRepository;
import java.math.BigInteger;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private CreditService creditService;

    @Override
    public Mono<TransactionEntity> saveAccountTransaction(TransactionRequest transactionRequest) {
        return accountService.findAccount(transactionRequest.getAccountNumberSource())
            .flatMap(account -> transactionRepository.countTransactions(account.getAccountNumber())
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

                    return transactionRepository.saveTransaction(TransactionBuilder.toTransactionEntity(
                        transactionRequest, account, counterTransactions));
                }));
    }

    @Override
    public Mono<TransactionEntity> saveCreditTransaction(TransactionRequest transactionRequest) {
        return creditService.findCredit(transactionRequest.getAccountNumberSource())
            .flatMap(credit -> transactionRepository.sumAmountTransactions(credit.getCreditNumber(),
                    transactionRequest.getType().name())
                .flatMap(sumAmountTransactions -> {
                    if (credit.getType().equals(CreditTypeEnum.CREDIT.name())
                        && transactionRequest.getType().equals(TransactionTypeEnum.PURCHASE)
                        && sumAmountTransactions.add(transactionRequest.getAmount())
                        .compareTo(credit.getCreditLimit()) > 0) {

                        return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST,
                            "The credit limit has been reached. : "
                                .concat(credit.getCreditLimit().toString())));
                    }
                    return transactionRepository.saveTransaction(TransactionBuilder.toTransactionEntity(
                        transactionRequest))
                        .flatMap(transactionEntity -> {
                            credit.setAvailableBalance(credit.getAvailableBalance().subtract(sumAmountTransactions));
                            return creditService.updateCredit(credit)
                                .flatMap(credit1 -> Mono.just(transactionEntity));
                        });
                }));
    }

    @Override
    public Mono<TransactionEntity> updateTransaction(TransactionRequest transactionRequest, String transactionId) {
        return transactionRepository.findTransaction(transactionId)
            .flatMap(transactionEntity -> transactionRepository.saveTransaction(
                TransactionBuilder.toTransactionEntity(transactionRequest, transactionEntity)))
            .switchIfEmpty(Mono.defer(() -> Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Transaction not found - transactionId: ".concat(transactionId)))));
    }

    @Override
    public Mono<TransactionEntity> findByTransactionNumber(BigInteger transactionNumber) {
        return transactionRepository.findTransaction(transactionNumber)
            .switchIfEmpty(Mono.defer(() ->
                Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Transaction not found - transactionNumber: ".concat(transactionNumber.toString()))))
            );
    }

    @Override
    public Flux<TransactionEntity> findByAccountNumberSource(BigInteger accountNumberSource) {
        return transactionRepository.findTransactions(accountNumberSource)
            .switchIfEmpty(Mono.defer(() ->
                Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Transaction not found - accountNumberSource: ".concat(accountNumberSource.toString()))))
            );
    }

    @Override
    public Flux<TransactionEntity> findByCustomerId(String customerId) {
        return transactionRepository.findTransactions(customerId)
            .switchIfEmpty(Mono.defer(() ->
                Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Transaction not found - customerId: ".concat(customerId))))
            );
    }


}
