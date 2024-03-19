package com.nttdata.transaction.builder;

import com.nttdata.transaction.api.request.TransactionAccountRequest;
import com.nttdata.transaction.api.request.TransactionCreditCardRequest;
import com.nttdata.transaction.api.request.TransactionCreditRequest;
import com.nttdata.transaction.api.request.TransactionDebitCardRequest;
import com.nttdata.transaction.api.request.TransactionTransferRequest;
import com.nttdata.transaction.enums.AssociatedTypeEnum;
import com.nttdata.transaction.enums.CardTypeEnum;
import com.nttdata.transaction.enums.StatusTypeEnum;
import com.nttdata.transaction.enums.TransactionAccountTypeEnum;
import com.nttdata.transaction.enums.TransactionTypeEnum;
import com.nttdata.transaction.model.Credit;
import com.nttdata.transaction.model.CreditCard;
import com.nttdata.transaction.model.Transaction;
import com.nttdata.transaction.model.account.Account;
import com.nttdata.transaction.model.account.AccountHolder;
import com.nttdata.transaction.model.debicard.DebitCard;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TransactionBuilder {
    TransactionBuilder() {
    }

    public static Transaction toTransactionEntity(TransactionAccountRequest transactionRequest,
        Account account, Integer counterTransactions) {
        return Transaction.builder()
            .number(generateNumber())
            .transactionType(transactionRequest.getType().name())
            .amount(transactionRequest.getAmount())
            .accountNumberSource(transactionRequest.getAccountNumberSource())
            .status(StatusTypeEnum.ACTIVE.name())
            .currency(account.getCurrency())
            .customerDocument(getCustomerDocumentPrimary(account.getAccountHolders()))
            .commission(getCommission(account, transactionRequest.getType().name(), counterTransactions))
            .dateCreated(LocalDateTime.now())
            .lastUpdated(LocalDateTime.now())
            .build();
    }

    public static Transaction toTransactionEntity(TransactionCreditRequest transactionRequest,
        Credit credit) {
        return Transaction.builder()
            .number(generateNumber())
            .transactionType(transactionRequest.getType().name())
            .amount(transactionRequest.getAmount())
            .status(StatusTypeEnum.ACTIVE.name())
            .accountNumberSource(transactionRequest.getAccountNumber())
            .currency(credit.getCurrency())
            .customerDocument(credit.getCustomerDocument())
            .commission(BigDecimal.valueOf(0.00))
            .dateCreated(LocalDateTime.now())
            .lastUpdated(LocalDateTime.now())
            .build();
    }

    public static Transaction toTransactionEntity(TransactionDebitCardRequest transactionRequest,
        DebitCard debitCard, Account account) {
        return Transaction.builder()
            .number(generateNumber())
            .transactionType(transactionRequest.getType().name())
            .amount(transactionRequest.getAmount())
            .status(StatusTypeEnum.ACTIVE.name())
            .cardType(CardTypeEnum.DEBIT.name())
            .cardNumber(debitCard.getCardNumber())
            .customerDocument(debitCard.getCustomerDocument())
            .accountNumberSource(account.getAccountNumber())
            .currency(account.getCurrency())
            .commission(new BigDecimal("0.00"))
            .dateCreated(LocalDateTime.now())
            .lastUpdated(LocalDateTime.now())
            .build();
    }

    public static Transaction toTransactionEntity(TransactionCreditCardRequest transactionRequest,
        CreditCard creditCard, Credit credit) {
        return Transaction.builder()
            .number(generateNumber())
            .transactionType(transactionRequest.getType().name())
            .amount(transactionRequest.getAmount())
            .status(StatusTypeEnum.ACTIVE.name())
            .cardType(CardTypeEnum.CREDIT.name())
            .cardNumber(creditCard.getCardNumber())
            .customerDocument(creditCard.getCustomerDocument())
            .accountNumberSource(credit.getCreditNumber())
            .currency(credit.getCurrency())
            .commission(new BigDecimal("0.00"))
            .dateCreated(LocalDateTime.now())
            .lastUpdated(LocalDateTime.now())
            .build();
    }

    public static Transaction toTransactionEntity(TransactionTransferRequest transactionRequest,
        Account account, Integer counterTransactions) {
        return Transaction.builder()
            .number(generateNumber())
            .transactionType(TransactionTypeEnum.WIRE_TRANSFER.name())
            .amount(transactionRequest.getAmount())
            .accountNumberSource(transactionRequest.getAccountNumberSource())
            .accountNumberTarget(transactionRequest.getAccountNumberTarget())
            .status(StatusTypeEnum.ACTIVE.name())
            .currency(account.getCurrency())
            .customerDocument(getCustomerDocumentPrimary(account.getAccountHolders()))
            .commission(getCommission(account, TransactionTypeEnum.WIRE_TRANSFER.name(), counterTransactions))
            .dateCreated(LocalDateTime.now())
            .lastUpdated(LocalDateTime.now())
            .build();
    }

    private static BigInteger generateNumber() {
        String accountNumber = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
        return new BigInteger(accountNumber);
    }

    private static BigInteger getCustomerDocumentPrimary(List<AccountHolder> accountHolders) {
        return accountHolders.stream()
            .filter(accountHolder -> accountHolder.getHolderType().equals(AssociatedTypeEnum.PRIMARY.name()))
            .findFirst()
            .map(AccountHolder::getCustomerDocument)
            .orElse(BigInteger.valueOf(0L));
    }

    private static BigDecimal getCommission(Account account, String transactionType, Integer counterTransactions) {

        if (counterTransactions > account.getLimitFreeMovements()) {
            if (transactionType.equals(TransactionAccountTypeEnum.DEPOSIT.name())
                || transactionType.equals(TransactionAccountTypeEnum.WITHDRAWAL.name())
                || transactionType.equals(TransactionAccountTypeEnum.WIRE_TRANSFER.name()))
                return account.getCommissionMovement();

            if (transactionType.equals(TransactionAccountTypeEnum.MAINTENANCE_CHARGE.name()))
                return account.getMaintenanceCommission();
        }

        return BigDecimal.valueOf(0.00);
    }

}
