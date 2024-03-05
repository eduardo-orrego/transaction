package com.nttdata.movement.business.impl;

import com.nttdata.movement.business.CreditMovementService;
import com.nttdata.movement.business.CreditService;
import com.nttdata.movement.model.CreditMovement;
import com.nttdata.movement.model.credit.Credit;
import com.nttdata.movement.model.enums.CreditMovementTypeEnum;
import com.nttdata.movement.model.enums.CreditTypeEnum;
import com.nttdata.movement.repository.CreditMovementRepository;
import java.math.BigDecimal;
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
            .flatMap(creditData -> {
                creditMovement.setOutstandingBalance(creditData.getOutstandingBalance());
                return creditMovementRepository.save(creditMovement);
            });
    }


    @Override
    public Flux<CreditMovement> getCreditMovements(String creditId) {
        return creditMovementRepository.getByCreditNumber(creditId);
    }

    private Mono<Credit> validateCreditMovement(CreditMovement creditMovement) {
        return creditService.getCreditByNumber(creditMovement.getCreditNumber())
            .map(creditData -> {
                BigDecimal outstandingBalance = creditData.getOutstandingBalance();

                if (creditData.getType().equals(CreditTypeEnum.CREDIT_CARD.name())) {
                    if (creditMovement.getTransactionType().name().equals(CreditMovementTypeEnum.PAYMENT.name())) {
                        creditData.setOutstandingBalance(outstandingBalance.subtract(creditMovement.getOutstandingBalance()));
                        creditData.getCreditCardInfo().setAvailableBalance(creditData.getCreditCardInfo()
                            .getAvailableBalance().add(creditMovement.getOutstandingBalance()));
                    } else {
                        creditData.setOutstandingBalance(outstandingBalance.add(creditMovement.getOutstandingBalance()));
                        creditData.getCreditCardInfo().setAvailableBalance(creditData.getCreditCardInfo()
                            .getAvailableBalance().subtract(creditMovement.getOutstandingBalance()));
                    }
                } else {
                    if (creditMovement.getTransactionType().name().equals(CreditMovementTypeEnum.PAYMENT.name())) {
                        creditData.setOutstandingBalance(outstandingBalance.subtract(creditMovement.getOutstandingBalance()));
                    } else {
                        creditData.setOutstandingBalance(outstandingBalance.add(creditMovement.getOutstandingBalance()));
                    }
                }
                return creditData;
            })
            .flatMap(creditService::putCredit);
    }
}
