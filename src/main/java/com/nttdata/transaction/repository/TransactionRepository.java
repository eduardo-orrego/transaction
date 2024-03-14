package com.nttdata.transaction.repository;

import com.nttdata.transaction.model.TransactionEntity;
import java.math.BigInteger;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TransactionRepository {

    Mono<TransactionEntity> findTransaction(String transactionId);

    Mono<TransactionEntity> findTransaction(BigInteger transactionNumber);

    Flux<TransactionEntity> findTransactions(BigInteger accountNumberSource);

    Flux<TransactionEntity> findTransactions(String customerId);

    Mono<TransactionEntity> saveTransaction(TransactionEntity transactionEntity);

    Mono<Boolean> findExistsTransaction(String transactionId);


}
