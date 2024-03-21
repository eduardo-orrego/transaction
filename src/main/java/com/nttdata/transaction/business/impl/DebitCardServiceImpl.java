package com.nttdata.transaction.business.impl;

import com.nttdata.transaction.business.DebitCardService;
import com.nttdata.transaction.client.DebitCardClient;
import com.nttdata.transaction.model.debicard.DebitCard;
import java.math.BigInteger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

/**
 * Class: DebitCardServiceImpl. <br/>
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
public class DebitCardServiceImpl implements DebitCardService {

  @Autowired
  private DebitCardClient debitCardClient;

  @Override
  public Mono<DebitCard> findDebitCard(BigInteger cardNumber) {
    return debitCardClient.getDebitCard(cardNumber)
      .switchIfEmpty(Mono.defer(() -> Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
        "No se encontraron datos de la tarjeta de debito"))))
      .doOnSuccess(credit -> log.info("Successful find Debit Card - cardNumber  "
        .concat(cardNumber.toString())));
  }
}
