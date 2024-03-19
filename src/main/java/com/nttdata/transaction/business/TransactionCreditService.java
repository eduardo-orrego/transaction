package com.nttdata.transaction.business;

import com.nttdata.transaction.api.request.TransactionCreditCardRequest;
import com.nttdata.transaction.api.request.TransactionCreditRequest;
import com.nttdata.transaction.model.Transaction;
import reactor.core.publisher.Mono;

public interface TransactionCreditService {
    Mono<Transaction> saveCreditTransaction(TransactionCreditRequest transactionRequest);

    Mono<Transaction> saveCreditCardTransaction(TransactionCreditCardRequest transactionRequest);
}
