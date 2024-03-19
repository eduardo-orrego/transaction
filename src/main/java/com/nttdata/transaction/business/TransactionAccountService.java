package com.nttdata.transaction.business;

import com.nttdata.transaction.api.request.TransactionRequest;
import com.nttdata.transaction.model.TransactionEntity;
import reactor.core.publisher.Mono;

public interface TransactionAccountService {
    Mono<TransactionEntity> saveAccountTransaction(TransactionRequest transactionRequest);
}
