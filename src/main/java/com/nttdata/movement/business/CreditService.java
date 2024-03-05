package com.nttdata.movement.business;

import com.nttdata.movement.model.credit.Credit;
import java.math.BigInteger;
import reactor.core.publisher.Mono;

public interface CreditService {
    Mono<Credit> getCreditByNumber(BigInteger creditNumber);

    Mono<Credit> putCredit(Credit credit);
}
