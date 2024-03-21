package com.nttdata.transaction.business.impl;

import com.nttdata.transaction.business.AccountService;
import com.nttdata.transaction.client.AccountClient;
import com.nttdata.transaction.model.account.Account;
import java.math.BigInteger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

/**
 * Class: AccountServiceImpl. <br/>
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
public class AccountServiceImpl implements AccountService {

  @Autowired
  private AccountClient accountClient;

  @Override
  public Mono<Account> findAccount(BigInteger accountNumber) {
    return accountClient.getAccountByNumber(accountNumber)
      .switchIfEmpty(Mono.defer(() -> Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
        "No se encontraron datos de la cuenta"))))
      .doOnSuccess(account -> log.info("Successful find - accountNumber: "
        .concat(account.getAccountNumber().toString())));
  }

  @Override
  public Mono<Account> updateAccount(Account account) {
    return accountClient.putAccount(account)
      .doOnSuccess(creditEntity -> log.info("Successful update - accountId: "
        .concat(account.getId())));
  }
}
