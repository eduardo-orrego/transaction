package com.nttdata.transaction.business.impl;

import com.nttdata.transaction.business.CreditService;
import com.nttdata.transaction.client.CreditClient;
import com.nttdata.transaction.model.Credit;
import java.math.BigInteger;
import java.time.LocalDate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class CreditServiceImpl implements CreditService {

    @Autowired
    private CreditClient creditClient;

    @Override
    public Mono<Credit> findCredit(BigInteger creditNumber) {
        return creditClient.getCreditByNumber(creditNumber)
            .switchIfEmpty(Mono.defer(() -> Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                "No se encontraron datos del credito"))))
            .doOnSuccess(credit -> log.info("Successful find Credit - Id  "
                .concat(credit.getCreditNumber().toString())));
    }

    @Override
    public Flux<Credit> findCredits(BigInteger customerDocument) {
        return creditClient.getCreditByCustomerDocument(customerDocument)
            .switchIfEmpty(Mono.defer(() -> Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                "No se encontraron creditos - customerDocument".concat(customerDocument.toString())))))
            .doOnComplete(() -> log.info("Successful find Credit - Id  "
                .concat(customerDocument.toString())));
    }

    @Override
    public Mono<Credit> updateCredit(Credit credit) {
        return creditClient.putCredit(credit)
            .doOnSuccess(creditEntity -> log.info("Successful update Credit - Id: "
                .concat(creditEntity.getId())));

    }

    @Override
    public Mono<Boolean> findExistsCreditOverdue(BigInteger customerDocument) {
        return this.findCredits(customerDocument)
            .any(credit -> LocalDate.now().isAfter(credit.getDueDate()));
    }

}
