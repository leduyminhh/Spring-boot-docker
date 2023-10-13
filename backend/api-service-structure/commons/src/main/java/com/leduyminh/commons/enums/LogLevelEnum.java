package com.leduyminh.commons.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum LogLevelEnum {

    INFO("INFO", "Thông tin"),
    WARNING("WARNING", "Cảnh báo"),
    ERROR("ERROR", "Lỗi");

    private final String value;
    private final String name;

    public static LogLevelEnum getByValue(String value) {
        return Arrays.stream(LogLevelEnum.values()).filter(e -> e.value.equals(value)).findFirst().orElse(null);
    }
}
