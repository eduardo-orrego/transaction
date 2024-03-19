package com.nttdata.transaction.repository;

import com.nttdata.transaction.model.Transaction;
import java.math.BigDecimal;
import java.math.BigInteger;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TransactionRepository {

    Mono<Transaction> findTransaction(String transactionId);

    Mono<Transaction> findTransaction(BigInteger transactionNumber);

    Flux<Transaction> findTransactions(BigInteger accountNumberSource);

    Flux<Transaction> findTransactionsByCustomerDocument(BigInteger customerDocument);

    Mono<Transaction> saveTransaction(Transaction transaction);

    Mono<Integer> countTransactions(BigInteger accountNumber);


    Mono<BigDecimal> sumAmountTransactions(String type, BigInteger accountNumber);
}
