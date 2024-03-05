package com.nttdata.movement.model.enums;

import lombok.Getter;

@Getter
public enum SignerTypeEnum {
    PRIMARY("PRIMARY"),
    SECONDARY("SECONDARY");

    private String value;

    SignerTypeEnum(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    public static SignerTypeEnum fromValue(String value) {
        for (SignerTypeEnum b : SignerTypeEnum.values()) {
            if (b.value.equals(value)) {
                return b;
            }
        }
        throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
}
