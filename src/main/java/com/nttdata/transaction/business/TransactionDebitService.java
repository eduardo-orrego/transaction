package com.nttdata.transaction.business;

import com.nttdata.transaction.api.request.TransactionAccountRequest;
import com.nttdata.transaction.api.request.TransactionDebitCardRequest;
import com.nttdata.transaction.api.request.TransactionTransferRequest;
import com.nttdata.transaction.model.Transaction;
import reactor.core.publisher.Mono;

/**
 * Class: TransactionDebitService. <br/>
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
public interface TransactionDebitService {
  Mono<Transaction> saveAccountTransaction(TransactionAccountRequest transactionRequest);

  Mono<Transaction> saveDebitCardTransaction(TransactionDebitCardRequest transactionRequest);

  Mono<Transaction> saveTransferTransaction(TransactionTransferRequest transactionRequest);
}
