package com.nttdata.transaction.business;

import com.nttdata.transaction.model.Transaction;
import java.math.BigInteger;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Class: TransactionService. <br/>
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
public interface TransactionService {

  Mono<Transaction> findByTransactionNumber(BigInteger transactionNumber);

  Flux<Transaction> findByAccountNumberSource(BigInteger accountNumberSource);

  Flux<Transaction> findByCustomerDocument(BigInteger customerDocument);
}
