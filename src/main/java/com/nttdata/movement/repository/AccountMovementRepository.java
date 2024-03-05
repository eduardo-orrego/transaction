package com.nttdata.movement.repository;

import com.nttdata.movement.model.AccountMovement;
import java.math.BigInteger;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface AccountMovementRepository extends ReactiveMongoRepository<AccountMovement, String> {

    Flux<AccountMovement> getByAccountNumber(String accountNumber);

    Mono<Long> countByAccountNumberAndCurrentMonth(BigInteger accountNumber);

}
