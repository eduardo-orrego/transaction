package com.nttdata.transaction.business;

import com.nttdata.transaction.model.Credit;
import java.math.BigInteger;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CreditService {
    Mono<Credit> findCredit(BigInteger creditNumber);

    Flux<Credit> findCredits(BigInteger customerDocument);

    Mono<Credit> updateCredit(Credit credit);

    Mono<Boolean> findExistsCreditOverdue(BigInteger customerDocument);
}
