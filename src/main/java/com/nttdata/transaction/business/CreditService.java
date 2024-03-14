package com.nttdata.transaction.business;

import com.nttdata.transaction.model.Credit;
import java.math.BigInteger;
import reactor.core.publisher.Mono;

public interface CreditService {
    Mono<Credit> findCredit(BigInteger creditNumber);
    Mono<Credit> updateCredit(Credit credit);
}
