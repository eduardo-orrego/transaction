package com.nttdata.transaction.business;

import com.nttdata.transaction.model.Credit;
import java.math.BigInteger;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Class: CreditService. <br/>
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
public interface CreditService {
  Mono<Credit> findCredit(BigInteger creditNumber);

  Flux<Credit> findCredits(BigInteger customerDocument);

  Mono<Credit> updateCredit(Credit credit);

  Mono<Boolean> findExistsCreditOverdue(BigInteger customerDocument);
}
