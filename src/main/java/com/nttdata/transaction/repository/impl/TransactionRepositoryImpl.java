package com.nttdata.transaction.repository.impl;

import com.nttdata.transaction.model.TransactionEntity;
import com.nttdata.transaction.repository.TransactionReactiveMongodb;
import com.nttdata.transaction.repository.TransactionRepository;
import java.math.BigDecimal;
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
    public Flux<TransactionEntity> findTransactionsByCustomerDocument(BigInteger customerDocument) {
        return transactionReactiveMongodb.findByCustomerDocument(customerDocument)
            .doOnComplete(() -> log.info("Successful find transactions  - customerDocument "
                .concat(customerDocument.toString())));

    }

    @Override
    public Mono<TransactionEntity> saveTransaction(TransactionEntity transactionEntity) {
        return transactionReactiveMongodb.save(transactionEntity)
            .doOnSuccess(transaction -> log.info("Successful save transaction - Id: ".concat(transaction.getId())));
    }

    @Override
    public Mono<Integer> countTransactions(BigInteger accountNumber) {
        return transactionReactiveMongodb.countByAccountNumberSource(accountNumber)
            .doOnSuccess(exists -> log.info("Successful count transactions - accountNumber: "
                .concat(accountNumber.toString())));
    }

    @Override
    public Mono<BigDecimal> sumAmountTransactions(String type, BigInteger accountNumber) {
        return transactionReactiveMongodb.sumAmountByTypeAndAccountNumberSource(type, accountNumber)
            .doOnSuccess(result -> log.info("Successful sum transactions amount - accountNumber: "
                .concat(accountNumber.toString())));
    }
}
