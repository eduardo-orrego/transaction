package com.nttdata.transaction.repository;

import com.nttdata.transaction.model.TransactionEntity;
import java.math.BigDecimal;
import java.math.BigInteger;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface TransactionReactiveMongodb extends ReactiveMongoRepository<TransactionEntity, String> {

    Mono<TransactionEntity> findByNumber(BigInteger transactionNumber);

    Flux<TransactionEntity> findByAccountNumberSource(BigInteger accountNumber);

    Flux<TransactionEntity> findByCustomerDocument(BigInteger customerDocument);

    @Aggregation(pipeline = {
        "{ $match: { numberAccount: ?0 } }",
        "{ $count: 'totalTransactions' }",
        "{ $default: { totalTransactions: 0 } }"
    })
    Mono<Integer> countByAccountNumberSource(BigInteger accountNumber);

    @Aggregation(pipeline = {
        "{ $match: { type: ?0, numberAccount: ?1 } }",
        "{ $group: { _id: null, totalAmount: { $sum: $amount } } }",
        "{ $default: { totalAmount: 0.00 } }"
    })
    Mono<BigDecimal> sumAmountByTypeAndAccountNumberSource(String type, BigInteger accountNumber);

}
