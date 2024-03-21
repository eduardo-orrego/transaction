package com.nttdata.transaction.business;

import com.nttdata.transaction.model.CreditCard;
import java.math.BigInteger;
import reactor.core.publisher.Mono;

/**
 * Class: CreditCardService. <br/>
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
public interface CreditCardService {
  Mono<CreditCard> findCreditCard(BigInteger cardNumber);
}
