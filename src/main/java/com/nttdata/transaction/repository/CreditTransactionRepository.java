package com.nttdata.transaction.repository;

import com.nttdata.transaction.model.CreditTransaction;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface CreditTransactionRepository extends ReactiveMongoRepository<CreditTransaction, String> {

    Flux<CreditTransaction> findByCreditNumber(String creditId);

}
