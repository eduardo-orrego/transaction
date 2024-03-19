package com.nttdata.transaction.builder;

import com.nttdata.transaction.api.request.TransactionRequest;
import com.nttdata.transaction.enums.TransactionTypeEnum;
import com.nttdata.transaction.model.Credit;
import com.nttdata.transaction.model.TransactionEntity;
import com.nttdata.transaction.model.account.Account;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class TransactionBuilder {
    TransactionBuilder() {
    }

    public static TransactionEntity toTransactionAccountEntity(TransactionRequest transactionRequest, Account account,
        Integer counterTransactions) {
        return TransactionEntity.builder()
            .number(Objects.nonNull(transactionRequest.getNumber())
                ? transactionRequest.getNumber()
                : generateNumber())
            .status(transactionRequest.getStatus().name())
            .transactionType(transactionRequest.getType().name())
            .amount(transactionRequest.getAmount())
            .currency(transactionRequest.getCurrency().name())
            .customerDocument(transactionRequest.getCustomerDocument())
            .cardType(Objects.nonNull(transactionRequest.getCard())
                ? transactionRequest.getCard().getType().name()
                : null)
            .cardNumber(Objects.nonNull(transactionRequest.getCard())
                ? transactionRequest.getCard().getNumber()
                : null)
            .accountNumberSource(transactionRequest.getAccountNumberSource())
            .accountNumberTarget(Objects.nonNull(transactionRequest.getAccountNumberTarget())
                ? transactionRequest.getAccountNumberTarget()
                : null)
            .commission(Objects.nonNull(transactionRequest.getCommission())
                ? transactionRequest.getCommission()
                : getCommission(transactionRequest, account, counterTransactions))
            .dateCreated(LocalDateTime.now())
            .lastUpdated(LocalDateTime.now())
            .build();
    }

    public static TransactionEntity toTransactionCreditEntity(TransactionRequest transactionRequest) {
        return TransactionEntity.builder()
            .number(Objects.nonNull(transactionRequest.getNumber())
                ? transactionRequest.getNumber()
                : generateNumber())
            .status(transactionRequest.getStatus().name())
            .transactionType(transactionRequest.getType().name())
            .amount(transactionRequest.getAmount())
            .currency(transactionRequest.getCurrency().name())
            .customerDocument(transactionRequest.getCustomerDocument())
            .cardType(Objects.nonNull(transactionRequest.getCard())
                ? transactionRequest.getCard().getType().name()
                : null)
            .cardNumber(Objects.nonNull(transactionRequest.getCard())
                ? transactionRequest.getCard().getNumber()
                : null)
            .accountNumberSource(transactionRequest.getAccountNumberSource())
            .accountNumberTarget(Objects.nonNull(transactionRequest.getAccountNumberTarget())
                ? transactionRequest.getAccountNumberTarget()
                : null)
            .commission(Objects.nonNull(transactionRequest.getCommission())
                ? transactionRequest.getCommission()
                : BigDecimal.valueOf(0.00))
            .dateCreated(LocalDateTime.now())
            .lastUpdated(LocalDateTime.now())
            .build();
    }

    public static TransactionEntity toTransactionEntity(TransactionRequest transactionRequest,
        TransactionEntity transactionEntity) {

        return TransactionEntity.builder()
            .id(transactionEntity.getId())
            .number(Objects.nonNull(transactionRequest.getNumber())
                ? transactionRequest.getNumber()
                : transactionEntity.getNumber())
            .status(transactionRequest.getStatus().name())
            .transactionType(transactionRequest.getType().name())
            .amount(transactionRequest.getAmount())
            .currency(transactionRequest.getCurrency().name())
            .customerDocument(transactionRequest.getCustomerDocument())
            .cardType(Objects.nonNull(transactionRequest.getCard())
                ? transactionRequest.getCard().getType().name()
                : transactionEntity.getCardType())
            .cardNumber(Objects.nonNull(transactionRequest.getCard())
                ? transactionRequest.getCard().getNumber()
                : transactionEntity.getCardNumber())
            .accountNumberSource(transactionRequest.getAccountNumberSource())
            .accountNumberTarget(Objects.nonNull(transactionRequest.getAccountNumberTarget())
                ? transactionRequest.getAccountNumberTarget()
                : transactionEntity.getAccountNumberTarget())
            .commission(Objects.nonNull(transactionRequest.getCommission())
                ? transactionRequest.getCommission()
                : transactionEntity.getCommission())
            .dateCreated(LocalDateTime.now())
            .lastUpdated(LocalDateTime.now())
            .build();

    }

    private static BigInteger generateNumber() {
        String accountNumber = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
        return new BigInteger(accountNumber);
    }

    private static BigDecimal getCommission(TransactionRequest transactionRequest, Account account,
        Integer counterTransactions) {

        if (counterTransactions > account.getLimitFreeMovements()) {
            if (transactionRequest.getType().equals(TransactionTypeEnum.DEPOSIT)
                || transactionRequest.getType().equals(TransactionTypeEnum.WITHDRAWAL))
                return account.getCommissionMovement();

            if (transactionRequest.getType().equals(TransactionTypeEnum.MAINTENANCE_CHARGE))
                return account.getMaintenanceCommission();
        }

        return BigDecimal.valueOf(0.00);
    }

}
