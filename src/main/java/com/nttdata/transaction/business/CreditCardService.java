package com.nttdata.transaction.business;

import com.nttdata.transaction.model.CreditCard;
import java.math.BigInteger;
import reactor.core.publisher.Mono;

public interface CreditCardService {
    Mono<CreditCard> findCreditCard(BigInteger cardNumber);
}
