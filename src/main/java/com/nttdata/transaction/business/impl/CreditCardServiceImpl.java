package com.nttdata.transaction.business.impl;

import com.nttdata.transaction.business.CreditCardService;
import com.nttdata.transaction.client.CreditCardClient;
import com.nttdata.transaction.model.CreditCard;
import java.math.BigInteger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

/**
 * Class: CreditCardServiceImpl. <br/>
 * <b>Bootcamp NTTDATA</b><br/>
 *
 * @author NTTDATA
 * @version 1.0
 *   <u>Developed by</u>:
 *   <ul>
 *   <li>Developer Carlos</li>
 *   </ul>
 * @since 1.0
 */
@Slf4j
@Service
public class CreditCardServiceImpl implements CreditCardService {

  @Autowired
  private CreditCardClient creditCardClient;

  @Override
  public Mono<CreditCard> findCreditCard(BigInteger cardNumber) {
    return creditCardClient.getCreditCard(cardNumber)
      .switchIfEmpty(Mono.defer(() -> Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
        "No se encontraron datos de la tarjeta de credito"))))
      .doOnSuccess(credit -> log.info("Successful find Credit Card - cardNumber  "
        .concat(cardNumber.toString())));
  }

}
