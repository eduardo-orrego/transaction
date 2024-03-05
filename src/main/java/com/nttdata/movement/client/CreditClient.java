package com.nttdata.movement.client;

import com.nttdata.movement.model.credit.Credit;
import java.math.BigInteger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class CreditClient {
    @Value("${microservices.credit.urlPaths.getCreditByNumber}")
    private String urlPathGetCreditByNumber;

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

    public Mono<Credit> putCredit(Credit credit) {
        return WebClient.create()
            .put()
            .uri(urlPathPutCredit)
            .bodyValue(credit)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(Credit.class);
    }
}