package com.nttdata.movement.business.impl;

import com.nttdata.movement.business.CreditService;
import com.nttdata.movement.client.CreditClient;
import com.nttdata.movement.model.credit.Credit;
import java.math.BigInteger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CreditServiceImpl implements CreditService {
    private final CreditClient creditClient;

    @Autowired
    public CreditServiceImpl(CreditClient creditClient) {
        this.creditClient = creditClient;
    }

    @Override
    public Mono<Credit> getCreditByNumber(BigInteger creditNumber) {
        return creditClient.getCreditByNumber(creditNumber)
            .switchIfEmpty(Mono.error(new RuntimeException("Numero de Credito no existe")));
    }

    @Override
    public Mono<Credit> putCredit(Credit credit) {
        return creditClient.putCredit(credit)
            .switchIfEmpty(Mono.error(new RuntimeException("Numero de Credito no existe")));
    }
}
