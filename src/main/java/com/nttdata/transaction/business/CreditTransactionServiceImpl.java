package com.nttdata.transaction.business;

import com.nttdata.transaction.model.CreditTransaction;
import com.nttdata.transaction.repository.CreditTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CreditTransactionServiceImpl implements CreditTransactionService {

    private final CreditTransactionRepository creditTransactionRepository;

    @Autowired
    public CreditTransactionServiceImpl(CreditTransactionRepository creditTransactionRepository) {
        this.creditTransactionRepository = creditTransactionRepository;
    }

    @Override
    public Mono<CreditTransaction> saveCreditTransaction(CreditTransaction creditTransaction) {
        return creditTransactionRepository.save(creditTransaction);
    }

    @Override
    public Flux<CreditTransaction> getCreditTransaction(String creditId) {
        return creditTransactionRepository.findByCreditNumber(creditId);
    }
}
