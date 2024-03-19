package com.nttdata.transaction.business;

import com.nttdata.transaction.model.Transaction;
import java.math.BigInteger;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TransactionService {

    Mono<Transaction> findByTransactionNumber(BigInteger transactionNumber);

    Flux<Transaction> findByAccountNumberSource(BigInteger accountNumberSource);

    Flux<Transaction> findByCustomerDocument(BigInteger customerDocument);
}
