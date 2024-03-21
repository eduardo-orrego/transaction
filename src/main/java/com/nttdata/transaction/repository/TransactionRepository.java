package com.nttdata.transaction.repository;

import com.nttdata.transaction.model.Transaction;
import java.math.BigDecimal;
import java.math.BigInteger;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Class: TransactionRepository. <br/>
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
public interface TransactionRepository {

  Mono<Transaction> findTransaction(String transactionId);

  Mono<Transaction> findTransaction(BigInteger transactionNumber);

  Flux<Transaction> findTransactions(BigInteger accountNumberSource);

  Flux<Transaction> findTransactionsByCustomerDocument(BigInteger customerDocument);

  Mono<Transaction> saveTransaction(Transaction transaction);

  Mono<Integer> countTransactions(BigInteger accountNumber);


  Mono<BigDecimal> sumAmountTransactions(String type, BigInteger accountNumber);
}
