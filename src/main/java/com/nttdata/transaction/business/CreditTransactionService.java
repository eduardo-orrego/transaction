package com.nttdata.transaction.business;

import com.nttdata.transaction.model.CreditTransaction;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CreditTransactionService {
    Mono<CreditTransaction> saveCreditTransaction(CreditTransaction creditTransaction);

    Flux<CreditTransaction> getCreditTransaction(String creditNumber);
}
