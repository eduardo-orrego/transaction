package com.nttdata.movement.business;

import com.nttdata.movement.model.CreditMovement;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CreditMovementService {
    Mono<CreditMovement> saveCreditMovement(CreditMovement creditMovement);

    Flux<CreditMovement> getCreditMovements(String creditNumber);
}
