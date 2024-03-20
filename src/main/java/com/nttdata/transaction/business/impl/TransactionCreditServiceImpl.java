package com.nttdata.transaction.business.impl;

import com.nttdata.transaction.api.request.TransactionCreditCardRequest;
import com.nttdata.transaction.api.request.TransactionCreditRequest;
import com.nttdata.transaction.builder.TransactionBuilder;
import com.nttdata.transaction.business.CreditCardService;
import com.nttdata.transaction.business.CreditService;
import com.nttdata.transaction.business.TransactionCreditService;
import com.nttdata.transaction.enums.CreditTypeEnum;
import com.nttdata.transaction.enums.TransactionTypeEnum;
import com.nttdata.transaction.model.Credit;
import com.nttdata.transaction.model.Transaction;
import com.nttdata.transaction.repository.TransactionRepository;
import java.math.BigDecimal;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Service
public class TransactionCreditServiceImpl implements TransactionCreditService {

    private final TransactionRepository transactionRepository;
    private final CreditService creditService;
    private final CreditCardService creditCardService;

    public TransactionCreditServiceImpl(TransactionRepository transactionRepository, CreditService creditService,
        CreditCardService creditCardService) {
        this.transactionRepository = transactionRepository;
        this.creditService = creditService;
        this.creditCardService = creditCardService;
    }

    @Override
    public Mono<Transaction> saveCreditTransaction(TransactionCreditRequest transactionRequest) {
        return creditService.findCredit(transactionRequest.getAccountNumber())
            .flatMap(credit -> this.validationCreditLimit(transactionRequest.getAmount(), credit))
            .flatMap(credit ->
                transactionRepository
                    .saveTransaction(TransactionBuilder.toTransactionEntity(transactionRequest, credit))
                    .flatMap(transactionEntity -> this.calculateCreditBalance(credit)
                        .flatMap(creditService::updateCredit)
                        .thenReturn(transactionEntity)
                    )
            );
    }

    @Override
    public Mono<Transaction> saveCreditCardTransaction(TransactionCreditCardRequest transactionRequest) {
        return creditCardService.findCreditCard(transactionRequest.getCardNumber())
            .flatMap(creditCard -> creditService.findCredit(creditCard.getCreditNumber())
                .flatMap(credit -> this.validationCreditLimit(transactionRequest.getAmount(), credit))
                .flatMap(credit ->
                    transactionRepository
                        .saveTransaction(TransactionBuilder.toTransactionEntity(transactionRequest,
                            creditCard, credit))
                        .flatMap(transactionEntity -> this.calculateCreditBalance(credit)
                            .flatMap(creditService::updateCredit)
                            .thenReturn(transactionEntity)
                        )
                ));
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

    private Mono<Credit> validationCreditLimit(BigDecimal transactionAmount, Credit credit) {
        if (credit.getType().equals(CreditTypeEnum.CREDIT.name())
            && credit.getAvailableBalance().add(transactionAmount)
            .compareTo(credit.getCreditLimit()) > 0) {

            return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "The credit limit has been reached. : ".concat(credit.getCreditLimit().toString())));
        }

        return Mono.just(credit);
    }

}
