package com.leduyminh.elastic.entities.requests;

import com.leduyminh.elastic.entities.ElasticQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BaseSearchRequest {
    private String keywords;

    private Integer pageNumber;

    private Integer pageSize;

    private List<ElasticQuery> queryList;
}
