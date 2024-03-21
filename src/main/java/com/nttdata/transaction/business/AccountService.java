package com.nttdata.transaction.business;

import com.nttdata.transaction.model.account.Account;
import java.math.BigInteger;
import reactor.core.publisher.Mono;

/**
 * Class: AccountService. <br/>
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
public interface AccountService {
  Mono<Account> findAccount(BigInteger accountNumber);

  Mono<Account> updateAccount(Account account);
}
