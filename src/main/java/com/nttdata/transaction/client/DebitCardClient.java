package com.nttdata.transaction.client;

import com.nttdata.transaction.model.debicard.DebitCard;
import java.math.BigInteger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * Class: DebitCardClient. <br/>
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
@Component
public class DebitCardClient {

  @Value("${microservices.debitCards.urlPaths.getDebitCards}")
  private String urlPathsGetDebitCards;

  public Mono<DebitCard> getDebitCard(BigInteger cardNumber) {

    return WebClient.create()
      .get()
      .uri(urlPathsGetDebitCards, cardNumber)
      .accept(MediaType.APPLICATION_JSON)
      .retrieve()
      .bodyToMono(DebitCard.class);

  }

}
