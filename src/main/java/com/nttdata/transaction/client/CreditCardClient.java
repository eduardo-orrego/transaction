package com.nttdata.transaction.client;

import com.nttdata.transaction.model.CreditCard;
import java.math.BigInteger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class CreditCardClient {

    @Value("${microservices.creditCards.urlPaths.getCreditCards}")
    private String urlPathsGetCreditCards;

    public Mono<CreditCard> getCreditCard(BigInteger documentNumber) {

        return WebClient.create()
            .get()
            .uri(urlPathsGetCreditCards, documentNumber)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(CreditCard.class);
    }

}
