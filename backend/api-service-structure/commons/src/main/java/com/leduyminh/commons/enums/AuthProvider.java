package com.leduyminh.commons.enums;


import lombok.Getter;

import java.util.Arrays;

@Getter
public enum AuthProvider {

    LOCAL("local", "Account tạo từ hệ thống"),
    FACEBOOK("facebook", "SSO FB"),
    GOOGLE("google", "SSO Google"),
    GITHUB("github", "SSO GitHub");

    private final String value;
    private final String name;

    AuthProvider(String value, String name) {
        this.value = value;
        this.name = name;
    }

    public static AuthProvider getByValue(String value) {
        return Arrays.stream(AuthProvider.values()).filter(e -> e.value.equals(value)).findFirst().orElse(null);
    }
}