package com.nttdata.movement.model.enums;

import lombok.Getter;

@Getter
public enum CreditMovementTypeEnum {
    PURCHASE("PURCHASE"),
    PAYMENT("PAYMENT");

    private final String value;

    CreditMovementTypeEnum(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    public static CreditMovementTypeEnum fromValue(String value) {
        for (CreditMovementTypeEnum b : CreditMovementTypeEnum.values()) {
            if (b.value.equals(value)) {
                return b;
            }
        }
        throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
}
