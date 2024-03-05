package com.nttdata.movement.business.impl;

import com.nttdata.movement.business.CreditMovementService;
import com.nttdata.movement.business.CreditService;
import com.nttdata.movement.model.CreditMovement;
import com.nttdata.movement.model.credit.Credit;
import com.nttdata.movement.model.enums.CreditMovementTypeEnum;
import com.nttdata.movement.model.enums.CreditTypeEnum;
import com.nttdata.movement.repository.CreditMovementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CreditMovementServiceImpl implements CreditMovementService {

    private final CreditMovementRepository creditMovementRepository;
    private final CreditService creditService;

    @Autowired
    public CreditMovementServiceImpl(CreditMovementRepository creditMovementRepository, CreditService creditService) {
        this.creditMovementRepository = creditMovementRepository;
        this.creditService = creditService;
    }

    @Override
    public Mono<CreditMovement> saveCreditMovement(CreditMovement creditMovement) {
        return this.validateCreditMovement(creditMovement)
            .flatMap(creditMovementRepository::save);
    }

    @Override
    public Flux<CreditMovement> getCreditMovements(String creditId) {
        return creditMovementRepository.getByCreditNumber(creditId);
    }

    private Mono<CreditMovement> validateCreditMovement(CreditMovement creditMovementData) {
        return creditService.getCreditByNumber(creditMovementData.getCreditNumber())
            .switchIfEmpty(Mono.error(new Exception("Numero de credito no encontrado")))
            .flatMap(creditData -> this.validateCredit(creditData, creditMovementData))
            .flatMap(creditService::putCredit)
            .map(creditData -> {
                creditMovementData.setOutstandingBalance(creditData.getOutstandingBalance());
                return creditMovementData;
            });
    }

    private Mono<Credit> validateCredit(Credit creditData, CreditMovement creditMovementData){
        if (creditData.getType().equals(CreditTypeEnum.CREDIT_CARD.name())) {
            return this.validateCreditCardCredit(creditData, creditMovementData);
        }
        if (creditData.getType().equals(CreditTypeEnum.LOAN.name())) {
            return this.validateLoanCredit(creditData, creditMovementData);
        }
        return Mono.just(creditData);
    }

    private Mono<Credit> validateLoanCredit(Credit creditData, CreditMovement creditMovementData) {
        if (creditMovementData.getTransactionType().name().equals(CreditMovementTypeEnum.PAYMENT.name())) {
            creditData.setOutstandingBalance(creditData.getOutstandingBalance()
                .subtract(creditMovementData.getOutstandingBalance()));
        } else {
            creditData.setOutstandingBalance(creditData.getOutstandingBalance()
                .add(creditMovementData.getOutstandingBalance()));
        }
        return Mono.just(creditData);
    }

    private Mono<Credit> validateCreditCardCredit(Credit creditData, CreditMovement creditMovementData) {
        if (creditMovementData.getTransactionType().name().equals(CreditMovementTypeEnum.PAYMENT.name())) {
            creditData.setOutstandingBalance(creditData.getOutstandingBalance()
                .subtract(creditMovementData.getOutstandingBalance()));
            creditData.getCreditCardInfo().setAvailableBalance(creditData.getCreditCardInfo().getAvailableBalance()
                .add(creditMovementData.getOutstandingBalance()));
        } else {
            creditData.setOutstandingBalance(creditData.getOutstandingBalance()
                .add(creditMovementData.getOutstandingBalance()));
            creditData.getCreditCardInfo().setAvailableBalance(creditData.getCreditCardInfo().getAvailableBalance()
                .subtract(creditMovementData.getOutstandingBalance()));
        }
        return Mono.just(creditData);
    }
}
