package com.nttdata.transaction.repository;

import com.nttdata.transaction.model.TransactionEntity;
import java.math.BigDecimal;
import java.math.BigInteger;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TransactionRepository {

    Mono<TransactionEntity> findTransaction(String transactionId);

    Mono<TransactionEntity> findTransaction(BigInteger transactionNumber);

    Flux<TransactionEntity> findTransactions(BigInteger accountNumberSource);

    Flux<TransactionEntity> findTransactionsByCustomerDocument(BigInteger customerDocument);

    Mono<TransactionEntity> saveTransaction(TransactionEntity transactionEntity);

    Mono<Integer> countTransactions(BigInteger accountNumber);


    Mono<BigDecimal> sumAmountTransactions(String type, BigInteger accountNumber);
}
