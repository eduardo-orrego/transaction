package com.nttdata.transaction.repository;

import com.nttdata.transaction.model.Transaction;
import java.math.BigDecimal;
import java.math.BigInteger;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Class: TransactionReactiveMongodb. <br/>
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
@Repository
public interface TransactionReactiveMongodb extends ReactiveMongoRepository<Transaction, String> {

  Mono<Transaction> findByNumber(BigInteger transactionNumber);

  Flux<Transaction> findByAccountNumberSource(BigInteger accountNumber);

  Flux<Transaction> findByCustomerDocument(BigInteger customerDocument);

  @Aggregation(pipeline = {
    "{ $match: { accountNumberSource: ?0 } }",
    "{ $count: 'totalTransactions' }"
  })
  Mono<Integer> countAmount(BigInteger accountNumberSource);

  @Aggregation(pipeline = {
    "{ $match: { transactionType: ?0, accountNumberSource: ?1 } }",
    "{ $group: { _id: null, totalAmount: { $sum: { $convert: { input: $amount, to: decimal } } }}}"
  })
  Mono<Double> sumAmount(String type, BigInteger accountNumberSource);

}
