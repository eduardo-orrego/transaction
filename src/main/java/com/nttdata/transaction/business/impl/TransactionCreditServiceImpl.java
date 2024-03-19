package com.nttdata.transaction.business.impl;

import com.nttdata.transaction.api.request.TransactionRequest;
import com.nttdata.transaction.builder.TransactionBuilder;
import com.nttdata.transaction.business.CreditService;
import com.nttdata.transaction.business.TransactionCreditService;
import com.nttdata.transaction.enums.CreditTypeEnum;
import com.nttdata.transaction.enums.TransactionTypeEnum;
import com.nttdata.transaction.model.Credit;
import com.nttdata.transaction.model.TransactionEntity;
import com.nttdata.transaction.repository.TransactionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Service
public class TransactionCreditServiceImpl implements TransactionCreditService {

    private final TransactionRepository transactionRepository;
    private final CreditService creditService;

    public TransactionCreditServiceImpl(TransactionRepository transactionRepository, CreditService creditService) {
        this.transactionRepository = transactionRepository;
        this.creditService = creditService;
    }

    @Override
    public Mono<TransactionEntity> saveCreditTransaction(TransactionRequest transactionRequest) {
        return creditService.findCredit(transactionRequest.getAccountNumberSource())
            .flatMap(credit -> this.validationCreditLimit(transactionRequest, credit))
            .flatMap(credit ->
                transactionRepository.saveTransaction(TransactionBuilder.toTransactionCreditEntity(transactionRequest))
                    .flatMap(transactionEntity -> this.calculateCreditBalance(credit)
                        .flatMap(creditService::updateCredit)
                        .thenReturn(transactionEntity)
                    )
            );
    }

    private Mono<Credit> calculateCreditBalance(Credit credit) {
        return transactionRepository.sumAmountTransactions(TransactionTypeEnum.PAYMENT.name(),
                credit.getCreditNumber())
            .flatMap(sumAmountPayment ->
                transactionRepository.sumAmountTransactions(TransactionTypeEnum.PURCHASE.name(),
                        credit.getCreditNumber())
                    .map(sumAmountPurchase -> {
                        credit.setOutstandingBalance(sumAmountPurchase.subtract(sumAmountPayment));
                        credit.setAvailableBalance(credit.getCreditLimit().subtract(credit.getOutstandingBalance()));
                        return credit;
                    }));
    }

    private Mono<Credit> validationCreditLimit(TransactionRequest transactionRequest, Credit credit) {
        if (credit.getType().equals(CreditTypeEnum.CREDIT.name())
            && transactionRequest.getType().equals(TransactionTypeEnum.PURCHASE)
            && credit.getAvailableBalance().add(transactionRequest.getAmount())
            .compareTo(credit.getCreditLimit()) > 0) {

            return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "The credit limit has been reached. : ".concat(credit.getCreditLimit().toString())));
        }
        return Mono.just(credit);
    }

}
