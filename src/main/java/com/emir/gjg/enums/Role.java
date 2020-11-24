package com.emir.gjg.enums;

public enum Role {
    BASIC_USER(1),
    MANAGER(2),
    ADMIN(3);  //the developer mode

    Role(int value) {
        this.value = value;
    }

    private final int value;

    public int toValue() {
        return value;
    }
}
