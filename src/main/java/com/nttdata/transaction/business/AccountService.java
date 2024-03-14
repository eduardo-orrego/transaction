package com.nttdata.transaction.business;

import com.nttdata.transaction.model.account.Account;
import java.math.BigInteger;
import reactor.core.publisher.Mono;

public interface AccountService {
    Mono<Account> findAccount(BigInteger accountNumber);
    Mono<Account> updateAccount(Account account);
}
