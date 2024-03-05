package com.nttdata.movement.business;

import com.nttdata.movement.model.AccountMovement;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountMovementService {

    Mono<AccountMovement> saveAccountMovement(AccountMovement accountMovement);

    Flux<AccountMovement> getAccountMovements(String accountNumber);

}
