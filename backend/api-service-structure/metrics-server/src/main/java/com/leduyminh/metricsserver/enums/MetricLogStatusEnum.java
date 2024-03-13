package com.leduyminh.metricsserver.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum MetricLogStatusEnum {
    CREATE("CREATE", "Thêm mới"),
    UPDATE("UPDATE", "Cập nhật"),
    ERROR("ERROR", "Lỗi");

    private final String value;
    private final String name;

    public static MetricLogStatusEnum getByValue(String value) {
        return Arrays.stream(MetricLogStatusEnum.values()).filter(e -> e.value.equals(value)).findFirst().orElse(null);
    }
}
