package com.nttdata.transaction.builder;

import com.nttdata.transaction.api.request.TransactionRequest;
import com.nttdata.transaction.model.TransactionEntity;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class TransactionBuilder {
    TransactionBuilder() {
    }

    public static TransactionEntity toTransactionEntity(TransactionRequest transactionRequest, String transactionId) {
        return TransactionEntity.builder()
            .id(transactionId)
            .number(Objects.nonNull(transactionRequest.getNumber())
                ? transactionRequest.getNumber()
                : generateNumber())
            .status(transactionRequest.getStatus().name())
            .transactionType(transactionRequest.getType().name())
            .amount(transactionRequest.getAmount())
            .currency(transactionRequest.getCurrency().name())
            .customerId(transactionRequest.getCustomerId())
            .cardType(transactionRequest.getCard().getType().name())
            .cardNumber(transactionRequest.getCard().getNumber())
            .accountNumberSource(transactionRequest.getAccountNumberSource())
            .accountNumberTarget(transactionRequest.getAccountNumberTarget())
            .maintenanceCommission(new BigDecimal("0.00"))
            .commissionMovement(new BigDecimal("0.00"))
            .dateCreated(LocalDateTime.now())
            .lastUpdated(LocalDateTime.now())
            .build();
    }


    private static BigInteger generateNumber() {
        String accountNumber = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
        return new BigInteger(accountNumber);
    }

}
