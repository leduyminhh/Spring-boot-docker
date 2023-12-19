package com.leduyminh.elastic.entities;

import com.leduyminh.elastic.enums.QueryType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ElasticQuery {
    private String fieldName;

    private Object fieldValue;

    private QueryType type;
}
