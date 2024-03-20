package com.nttdata.transaction.client;

import com.nttdata.transaction.model.debicard.DebitCard;
import java.math.BigInteger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class DebitCardClient {

    @Value("${microservices.creditCards.urlPaths.getDebitCards}")
    private String urlPathsGetDebitCards;

    public Mono<DebitCard> getDebitCard(BigInteger documentNumber) {

        return WebClient.create()
            .get()
            .uri(urlPathsGetDebitCards, documentNumber)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(DebitCard.class);

    }

}
