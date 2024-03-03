package com.nttdata.transaction.business;

import com.nttdata.transaction.model.AccountTransaction;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountTransactionService {

    Mono<AccountTransaction> saveAccountTransaction(AccountTransaction accountTransaction);

    Flux<AccountTransaction> getAccountTransaction(String accountNumber);

}
