package com.nttdata.transaction.business;

import com.nttdata.transaction.model.AccountTransaction;
import com.nttdata.transaction.repository.AccountTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AccountTransactionServiceImpl implements AccountTransactionService {

    private final AccountTransactionRepository accountTransactionRepository;

    @Autowired
    public AccountTransactionServiceImpl(AccountTransactionRepository accountTransactionRepository) {
        this.accountTransactionRepository = accountTransactionRepository;
    }

    @Override
    public Mono<AccountTransaction> saveAccountTransaction(AccountTransaction accountTransaction) {
        return accountTransactionRepository.save(accountTransaction);
    }

    @Override
    public Flux<AccountTransaction> getAccountTransaction(String accountNumber) {
        return accountTransactionRepository.findByAccountNumber(accountNumber);
    }

}
