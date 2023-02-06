package com.leduyminh.commons.enums;

public enum StatusEnum {
    ACTIVE("ACTIVE", 1),
    DELETED("DELETED", 0)
    ;

    private String name;
    private Integer value;

    StatusEnum(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }
}
