package com.nttdata.transaction.repository;

import com.nttdata.transaction.model.TransactionEntity;
import java.math.BigInteger;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface TransactionReactiveMongodb extends ReactiveMongoRepository<TransactionEntity, String> {

    Mono<TransactionEntity> findByNumber(BigInteger transactionNumber);

    Flux<TransactionEntity> findByAccountNumberSource(BigInteger accountNumber);

    Flux<TransactionEntity> findByCustomerId(String customerId);

}
