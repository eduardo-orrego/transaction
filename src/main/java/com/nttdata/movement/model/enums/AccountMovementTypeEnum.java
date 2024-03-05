package com.nttdata.movement.model.enums;

import lombok.Getter;

@Getter
public enum AccountMovementTypeEnum {
    DEPOSIT("DEPOSIT"),
    WITHDRAWAL("WITHDRAWAL"),
    WIRE_TRANSFER("WIRE_TRANSFER");

    private final String value;

    AccountMovementTypeEnum(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    public static AccountMovementTypeEnum fromValue(String value) {
        for (AccountMovementTypeEnum b : AccountMovementTypeEnum.values()) {
            if (b.value.equals(value)) {
                return b;
            }
        }
        throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
}
