package com.nttdata.transaction.repository;

import com.nttdata.transaction.model.AccountTransaction;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface AccountTransactionRepository extends ReactiveMongoRepository<AccountTransaction, String> {

    Flux<AccountTransaction> findByAccountNumber(String holderId);

}
