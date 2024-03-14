package com.nttdata.transaction.repository.impl;

import com.nttdata.transaction.model.TransactionEntity;
import com.nttdata.transaction.repository.TransactionReactiveMongodb;
import com.nttdata.transaction.repository.TransactionRepository;
import java.math.BigInteger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Repository
public class TransactionRepositoryImpl implements TransactionRepository {

    @Autowired
    private TransactionReactiveMongodb transactionReactiveMongodb;

    @Override
    public Mono<TransactionEntity> findTransaction(String transactionId) {
        return transactionReactiveMongodb.findById(transactionId)
            .doOnSuccess(transaction -> log.info("Successful save transaction - transactionId: "
                .concat(transactionId)));
    }

    @Override
    public Mono<TransactionEntity> findTransaction(BigInteger transactionNumber) {
        return transactionReactiveMongodb.findByNumber(transactionNumber)
            .doOnSuccess(transaction -> log.info("Successful save transaction - transactionNumber: "
                .concat(transactionNumber.toString())));

    }

    @Override
    public Flux<TransactionEntity> findTransactions(BigInteger accountSource) {
        return transactionReactiveMongodb.findByAccountNumberSource(accountSource)
            .doOnComplete(() -> log.info("Successful find transactions  - accountSource "
                .concat(accountSource.toString())));

    }

    @Override
    public Flux<TransactionEntity> findTransactions(String customerId) {
        return transactionReactiveMongodb.findByCustomerId(customerId)
            .doOnComplete(() -> log.info("Successful find transactions  - customerId ".concat(customerId)));

    }

    @Override
    public Mono<TransactionEntity> saveTransaction(TransactionEntity transactionEntity) {
        return transactionReactiveMongodb.save(transactionEntity)
            .doOnSuccess(transaction -> log.info("Successful save transaction - Id: ".concat(transaction.getId())));
    }

    @Override
    public Mono<Boolean> findExistsTransaction(String transactionId) {
        return transactionReactiveMongodb.existsById(transactionId)
            .doOnSuccess(exists -> log.info("Successful find exists transaction - Id: ".concat(transactionId)));
    }
}
