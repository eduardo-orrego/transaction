package com.nttdata.transaction.client;

import com.nttdata.transaction.model.Credit;
import java.math.BigInteger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class CreditClient {

    @Value("${microservices.credit.urlPaths.getCreditByNumber}")
    private String urlPathGetCreditByNumber;

    @Value("${microservices.credit.urlPaths.getCreditByCustomerDocument}")
    private String urlPatGetCreditByCustomerDocument;

    @Value("${microservices.credit.urlPaths.putCredit}")
    private String urlPathPutCredit;

    public Mono<Credit> getCreditByNumber(BigInteger creditNumber) {
        return WebClient.create()
            .get()
            .uri(urlPathGetCreditByNumber, creditNumber)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(Credit.class);
    }

    public Flux<Credit> getCreditByCustomerDocument(BigInteger customerDocument) {
        return WebClient.create()
            .get()
            .uri(uriBuilder -> uriBuilder
                .path(urlPatGetCreditByCustomerDocument)
                .queryParam("customerDocument", customerDocument)
                .build())
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToFlux(Credit.class);
    }

    public Mono<Credit> putCredit(Credit credit) {
        return WebClient.create()
            .put()
            .uri(urlPathPutCredit, credit.getId())
            .bodyValue(credit)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(Credit.class);
    }

}
