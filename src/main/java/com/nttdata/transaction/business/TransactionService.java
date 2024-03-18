package com.nttdata.transaction.business;

import com.nttdata.transaction.api.request.TransactionRequest;
import com.nttdata.transaction.model.TransactionEntity;
import java.math.BigInteger;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TransactionService {

    Mono<TransactionEntity> findByTransactionNumber(BigInteger transactionNumber);

    Flux<TransactionEntity> findByAccountNumberSource(BigInteger accountNumberSource);

    Flux<TransactionEntity> findByCustomerId(String customerId);

    Mono<TransactionEntity> saveAccountTransaction(TransactionRequest transactionRequest);

    Mono<TransactionEntity> saveCreditTransaction(TransactionRequest transactionRequest);

    Mono<TransactionEntity> updateTransaction(TransactionRequest transactionRequest, String transactionId);
}
