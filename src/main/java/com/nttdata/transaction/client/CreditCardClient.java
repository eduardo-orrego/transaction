package com.nttdata.transaction.client;

import com.nttdata.transaction.model.CreditCard;
import java.math.BigInteger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * Class: CreditCardClient. <br/>
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
public class CreditCardClient {

  @Value("${microservices.creditCards.urlPaths.getCreditCards}")
  private String urlPathsGetCreditCards;

  public Mono<CreditCard> getCreditCard(BigInteger cardNumber) {

    return WebClient.create()
      .get()
      .uri(urlPathsGetCreditCards, cardNumber)
      .accept(MediaType.APPLICATION_JSON)
      .retrieve()
      .bodyToMono(CreditCard.class);
  }

}
