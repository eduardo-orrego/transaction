package com.nttdata.movement.business;

import com.nttdata.movement.model.account.Account;
import java.math.BigInteger;
import reactor.core.publisher.Mono;

public interface AccountService {
    Mono<Account> getAccountByAccountNumber(BigInteger accountNumber);

    Mono<Account> putAccount(Account account);

}
