package com.leduyminh.elastic.entities.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Map;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BaseIndexRequest {
    private String collection;

    private String fieldPK;

    private List<Map<String, Object>> data;

    private Map<String, String> mappings; // for no-sql documents
}
