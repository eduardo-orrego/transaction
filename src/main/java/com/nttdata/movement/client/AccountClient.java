package com.nttdata.movement.client;

import com.nttdata.movement.model.account.Account;
import java.math.BigInteger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class AccountClient {

    @Value("${microservices.account.urlPaths.getAccountByNumber}")
    private String urlPathGetAccountByNumber;

    @Value("${microservices.account.urlPaths.putAccount}")
    private String urlPathPutAccount;

    public Mono<Account> getAccountByNumber(BigInteger accountNumber) {
        return WebClient.create()
            .get()
            .uri(urlPathGetAccountByNumber, accountNumber)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(Account.class);
    }

    public Mono<Account> putAccount(Account account) {
        return WebClient.create()
            .put()
            .uri(urlPathPutAccount)
            .bodyValue(account)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(Account.class);
    }
}
