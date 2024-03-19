package com.nttdata.transaction.business.impl;

import com.nttdata.transaction.api.request.TransactionRequest;
import com.nttdata.transaction.builder.TransactionBuilder;
import com.nttdata.transaction.business.AccountService;
import com.nttdata.transaction.business.TransactionAccountService;
import com.nttdata.transaction.model.TransactionEntity;
import com.nttdata.transaction.model.account.Account;
import com.nttdata.transaction.repository.TransactionRepository;
import java.time.LocalDate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Service
public class TransactionAccountServiceImpl implements TransactionAccountService {

    private final TransactionRepository transactionRepository;
    private final AccountService accountService;

    public TransactionAccountServiceImpl(TransactionRepository transactionRepository, AccountService accountService) {
        this.transactionRepository = transactionRepository;
        this.accountService = accountService;
    }

    @Override
    public Mono<TransactionEntity> saveAccountTransaction(TransactionRequest transactionRequest) {
        return accountService.findAccount(transactionRequest.getAccountNumberSource())
            .flatMap(account -> transactionRepository.countTransactions(account.getAccountNumber())
                .flatMap(counterTransactions -> this.validationAccountLimitMovement(account, counterTransactions)
                    .map(accountValidated -> TransactionBuilder.toTransactionAccountEntity(
                        transactionRequest, accountValidated, counterTransactions))
                    .flatMap(transactionRepository::saveTransaction))
            );
    }

    private Mono<Account> validationAccountLimitMovement(Account account, Integer counterTransactions) {
        if (account.getMonthlyLimitMovement() > 0
            && counterTransactions > account.getMonthlyLimitMovement()) {
            return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "Maximum monthly transaction limit has been reached - Maximum limit :"
                    .concat(account.getMonthlyLimitMovement().toString())));
        }

        if (account.getSpecificDayMonthMovement() > 0
            && !account.getSpecificDayMonthMovement().equals(LocalDate.now().getDayOfMonth())) {
            return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "Transactions are permitted on : ".concat(
                    account.getSpecificDayMonthMovement().toString())));
        }

        return Mono.just(account);
    }
}
