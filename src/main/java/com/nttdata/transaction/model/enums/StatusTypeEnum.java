package com.nttdata.transaction.model.enums;

import lombok.Getter;

@Getter
public enum StatusTypeEnum {
    ACTIVE("active"),
    BUSINESS("business");

    private String value;

    StatusTypeEnum(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    public static StatusTypeEnum fromValue(String value) {
        for (StatusTypeEnum b : StatusTypeEnum.values()) {
            if (b.value.equals(value)) {
                return b;
            }
        }
        throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
}
