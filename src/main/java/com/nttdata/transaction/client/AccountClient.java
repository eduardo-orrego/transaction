package com.nttdata.transaction.client;

import com.nttdata.transaction.model.account.Account;
import java.math.BigInteger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * Class: AccountClient. <br/>
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
@Component
public class AccountClient {

  @Value("${microservices.accounts.urlPaths.getAccountByNumber}")
  private String urlPathGetAccountByNumber;

  @Value("${microservices.accounts.urlPaths.putAccount}")
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
      .uri(urlPathPutAccount, account.getId())
      .bodyValue(account)
      .accept(MediaType.APPLICATION_JSON)
      .retrieve()
      .bodyToMono(Account.class);
  }

}
