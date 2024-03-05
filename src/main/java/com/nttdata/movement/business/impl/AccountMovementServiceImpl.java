package com.nttdata.movement.business.impl;

import com.nttdata.movement.business.AccountMovementService;
import com.nttdata.movement.business.AccountService;
import com.nttdata.movement.model.AccountMovement;
import com.nttdata.movement.model.account.Account;
import com.nttdata.movement.model.enums.AccountMovementTypeEnum;
import com.nttdata.movement.model.enums.AccountTypeEnum;
import com.nttdata.movement.repository.AccountMovementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AccountMovementServiceImpl implements AccountMovementService {

    private final AccountMovementRepository accountMovementRepository;
    private final AccountService accountService;

    @Autowired
    public AccountMovementServiceImpl(AccountMovementRepository accountMovementRepository,
        AccountService accountService) {
        this.accountMovementRepository = accountMovementRepository;
        this.accountService = accountService;
    }

    @Override
    public Mono<AccountMovement> saveAccountMovement(AccountMovement accountMovement) {
        return this.validateAccountMovement(accountMovement)
            .flatMap(accountMovementRepository::save);

    }

    @Override
    public Flux<AccountMovement> getAccountMovements(String accountNumber) {
        return accountMovementRepository.getByAccountNumber(accountNumber);
    }

    private Mono<AccountMovement> validateAccountMovement(AccountMovement accountMovement) {
        return accountService.getAccountByAccountNumber(accountMovement.getAccountNumber())
            .flatMap(accountData ->
                accountMovementRepository.countByAccountNumber(accountMovement.getAccountNumber())
                    .flatMap(countLong ->
                        this.validateAccount(accountData, accountMovement, countLong)
                            .flatMap(accountService::putAccount)
                            .flatMap(accountNewData ->
                                this.validateAccountMovement(accountNewData, accountMovement, countLong)))
            );
    }

    private Mono<AccountMovement> validateAccountMovement(Account accountData, AccountMovement accountMovement,
        Long countMovement) {

        if (accountMovement.getAccountType().name().equals(AccountTypeEnum.SAVINGS.name())
            && !accountMovement.getTransactionType().name().equals(AccountMovementTypeEnum.WIRE_TRANSFER.name())
            && countMovement > accountData.getSavingsInfo().getNumberFreeTransactions()) {
            accountMovement.setTransactionFee(accountData.getSavingsInfo().getTransactionFee());
        }
        accountMovement.setAvailableBalance(accountData.getAvailableBalance());
        return Mono.just(accountMovement);
    }

    private Mono<Account> validateAccount(Account accountData, AccountMovement accountMovement, Long countMovement) {
        if (accountMovement.getAccountType().name().equals(AccountTypeEnum.SAVINGS.name())) {
            return this.validateAccountSavings(accountMovement, accountData, countMovement);
        }
        if (accountMovement.getAccountType().name().equals(AccountTypeEnum.TERM_DEPOSIT.name())) {
            return this.validateAccountTermDeposit(accountData, countMovement);
        }
        return Mono.just(accountData);
    }

    private Mono<Account> validateAccountSavings(AccountMovement accountMovement, Account accountData,
        Long countMovement) {

        if (!accountMovement.getTransactionType().name().equals(AccountMovementTypeEnum.WIRE_TRANSFER.name())
            && countMovement >= accountData.getSavingsInfo().getMonthlyMovements()) {
            return Mono.error(new RuntimeException("Se ha llegado al limite de movimientos"));
        }

        if (accountMovement.getTransactionType().name().equals(AccountMovementTypeEnum.DEPOSIT.name())) {
            accountData.setAvailableBalance(accountData.getAvailableBalance().add(accountMovement.getAmount()));
        }

        if (accountMovement.getTransactionType().name().equals(AccountMovementTypeEnum.WITHDRAWAL.name())) {
            accountData.setAvailableBalance(accountData.getAvailableBalance().subtract(accountMovement.getAmount()));
        }
        return Mono.just(accountData);
    }

    private Mono<Account> validateAccountTermDeposit(Account accountData, Long countMovement) {
        if (countMovement >= 1) {
            return Mono.error(new RuntimeException("Se ha llegado al limite de movimientos"));
        }
        return Mono.just(accountData);

    }

}
