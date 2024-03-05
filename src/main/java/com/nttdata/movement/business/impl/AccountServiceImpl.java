package com.nttdata.movement.business.impl;

import com.nttdata.movement.business.AccountService;
import com.nttdata.movement.client.AccountClient;
import com.nttdata.movement.model.account.Account;
import java.math.BigInteger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountClient accountClient;

    @Autowired
    public AccountServiceImpl(AccountClient accountClient) {
        this.accountClient = accountClient;
    }

    @Override
    public Mono<Account> getAccountByAccountNumber(BigInteger accountNumber) {
        return accountClient.getAccountByNumber(accountNumber)
            .switchIfEmpty(Mono.error(new RuntimeException("Numero de cuenta no existe")));
    }

    @Override
    public Mono<Account> putAccount(Account account) {
        return accountClient.putAccount(account);
    }
}



