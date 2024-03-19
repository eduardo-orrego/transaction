package com.nttdata.transaction.business.impl;

import com.nttdata.transaction.api.request.TransactionRequest;
import com.nttdata.transaction.builder.TransactionBuilder;
import com.nttdata.transaction.business.TransactionService;
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
    public Flux<TransactionEntity> findByCustomerDocument(BigInteger customerDocument) {
        return transactionRepository.findTransactionsByCustomerDocument(customerDocument)
            .switchIfEmpty(Mono.defer(() ->
                Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Transaction not found - customerDocument: ".concat(customerDocument.toString()))))
            );
    }


}
