package com.nttdata.transaction.business;

import com.nttdata.transaction.model.debicard.DebitCard;
import java.math.BigInteger;
import reactor.core.publisher.Mono;

public interface DebitCardService {
    Mono<DebitCard> findDebitCard(BigInteger cardNumber);
}
