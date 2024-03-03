package com.nttdata.transaction.model.enums;

import lombok.Getter;

@Getter
public enum CreditTransactionTypeEnum {
    PURCHASE("purchase"),
    PAYMENT("payment");

    private final String value;

    CreditTransactionTypeEnum(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    public static CreditTransactionTypeEnum fromValue(String value) {
        for (CreditTransactionTypeEnum b : CreditTransactionTypeEnum.values()) {
            if (b.value.equals(value)) {
                return b;
            }
        }
        throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
}
