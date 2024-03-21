package com.nttdata.transaction.business;

import com.nttdata.transaction.api.request.TransactionCreditCardRequest;
import com.nttdata.transaction.api.request.TransactionCreditRequest;
import com.nttdata.transaction.model.Transaction;
import reactor.core.publisher.Mono;

/**
 * Class: TransactionCreditService. <br/>
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
public interface TransactionCreditService {
  Mono<Transaction> saveCreditTransaction(TransactionCreditRequest transactionRequest);

  Mono<Transaction> saveCreditCardTransaction(TransactionCreditCardRequest transactionRequest);
}
