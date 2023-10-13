package com.leduyminh.aspose.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum ExportType {
    PDF("PDF", "Xuất pdf"),
    WORD("WORD", "Xuất word"),
    EXCEL("EXCEL", "Xuất excel");
    private final String value;
    private final String name;
    public static ExportType getByValue(String value) {
        return Arrays.stream(ExportType.values()).filter(e -> e.value.equals(value)).findFirst().orElse(null);
    }
}
