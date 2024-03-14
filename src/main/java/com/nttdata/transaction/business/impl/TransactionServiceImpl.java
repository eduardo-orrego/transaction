package com.nttdata.transaction.business.impl;

import com.nttdata.transaction.api.request.TransactionRequest;
import com.nttdata.transaction.builder.TransactionBuilder;
import com.nttdata.transaction.business.AccountService;
import com.nttdata.transaction.business.CreditService;
import com.nttdata.transaction.business.TransactionService;
import com.nttdata.transaction.client.AccountClient;
import com.nttdata.transaction.client.CreditClient;
import com.nttdata.transaction.model.TransactionEntity;
import com.nttdata.transaction.repository.TransactionRepository;
import java.math.BigInteger;
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

    @Override
    public Mono<TransactionEntity> saveTransaction(TransactionRequest transactionRequest) {
        return accountService.findAccount(transactionRequest.getAccountNumberSource())
            .flatMap(account -> transactionRepository.saveTransaction(
                TransactionBuilder.toTransactionEntity(transactionRequest, null))
            );
    }

    @Override
    public Mono<TransactionEntity> updateTransaction(TransactionRequest transactionRequest, String transactionId) {
        return transactionRepository.findExistsTransaction(transactionId)
            .flatMap(aBoolean -> Boolean.TRUE.equals(aBoolean)
                ? transactionRepository.saveTransaction(TransactionBuilder.toTransactionEntity(transactionRequest,
                transactionId))
                : Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Transaction not found - transactionId: ".concat(transactionId)))
            );
    }
}
