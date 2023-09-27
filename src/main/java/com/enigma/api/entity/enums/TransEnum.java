package com.enigma.api.entity.enums;
public enum TransEnum {
    EAT_IN("1"),
    ONLINE("2"),
    TAKE_AWAY("3");

    private final String transEnum;
    TransEnum(String transactionTypeEnum) {
        this.transEnum = transactionTypeEnum;
    }

    public String getValue() {
        return transEnum;
    }

    public static TransEnum fromValue(String value) {
        for (TransEnum type : values()) {
            if (type.transEnum.equals(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid transaction type: " + value);
    }

}