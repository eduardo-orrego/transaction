package com.nttdata.movement.business.impl;

import com.nttdata.movement.business.AccountMovementService;
import com.nttdata.movement.business.AccountService;
import com.nttdata.movement.model.AccountMovement;
import com.nttdata.movement.model.account.Account;
import com.nttdata.movement.model.enums.AccountMovementTypeEnum;
import com.nttdata.movement.model.enums.AccountTypeEnum;
import com.nttdata.movement.repository.AccountMovementRepository;
import java.math.BigDecimal;
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
            .flatMap(accountData -> {
                accountMovement.setAvailableBalance(accountData.getAvailableBalance());
                return accountMovementRepository.save(accountMovement);
            });

    }

    @Override
    public Flux<AccountMovement> getAccountMovements(String accountNumber) {
        return accountMovementRepository.getByAccountNumber(accountNumber);
    }

    private Mono<Account> validateAccountMovement(AccountMovement accountMovement) {
        return accountService.getAccountByAccountNumber(accountMovement.getAccountNumber())
            .flatMap(accountData ->
                accountMovementRepository.countByAccountNumberAndCurrentMonth(accountMovement.getAccountNumber())
                    .flatMap(countLong -> {
                        if (accountMovement.getAccountType().name().equals(AccountTypeEnum.SAVINGS.name())
                            && countLong >= accountData.getSavingsInfo().getMonthlyMovements()) {
                            return Mono.error(new RuntimeException("Se puero el limite de movimientos"));
                        }
                        if (accountMovement.getAccountType().name().equals(AccountTypeEnum.TERM_DEPOSIT.name())
                            && countLong >= 1) {
                            return Mono.error(new RuntimeException("Se supero el limite de movimientos"));
                        }
                        return Mono.just(accountData);
                    }))
            .map(accountData -> {
                BigDecimal availableBalance = accountData.getAvailableBalance();
                if (accountMovement.getTransactionType().name().equals(AccountMovementTypeEnum.DEPOSIT.name())) {
                    accountData.setAvailableBalance(availableBalance.add(accountMovement.getAmount()));
                } else {
                    accountData.setAvailableBalance(availableBalance.subtract(accountMovement.getAmount()));
                }
                return accountData;
            })
            .flatMap(accountService::putAccount);
    }
}
