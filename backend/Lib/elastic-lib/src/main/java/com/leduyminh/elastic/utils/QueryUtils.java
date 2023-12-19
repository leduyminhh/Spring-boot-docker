package com.leduyminh.elastic.utils;

import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.index.query.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class QueryUtils {

    public static void match(String field, String value, BoolQueryBuilder builder) {
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        QueryBuilder query = QueryBuilders.matchQuery(field, value).operator(Operator.AND);
        queryBuilder.should(query);
        builder.filter(queryBuilder);
    }

    public static void nestedTerm(String field, String term, String value, BoolQueryBuilder builder) {
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        QueryBuilder query = QueryBuilders.termQuery(String.join(".", field, term, "keyword"), value);
        queryBuilder.must(query);
        NestedQueryBuilder nestedQueryBuilder = QueryBuilders.nestedQuery(field, queryBuilder, ScoreMode.Avg);
        builder.filter(nestedQueryBuilder);
    }

    public static void wildcard(String field, String value, BoolQueryBuilder builder) {
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        String[] keywords = value.split(" ");
        for (String keyword : keywords) {
            QueryBuilder query = QueryBuilders.wildcardQuery(field, "*" + keyword.toLowerCase() + "*");
            queryBuilder.should(query);
        }
        builder.filter(queryBuilder);
    }

    public static void wildcardMultiple(List<String> fieldList, Object value, BoolQueryBuilder builder) {
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        String keywords = String.valueOf(value).toLowerCase();
        String[] terms = keywords.split(" ");
        String queryString = Arrays.stream(terms).map(term -> "*" + term + "*").collect(Collectors.joining(" "));
        fieldList.forEach((key) -> {
            QueryBuilder query = QueryBuilders.queryStringQuery(queryString).field(key).defaultOperator(Operator.AND);
            queryBuilder.should(query);
        });
        queryBuilder.minimumShouldMatch(1);
        builder.filter(queryBuilder);
    }

    public static void range(String field, Object from, Object to, BoolQueryBuilder builder) {
        QueryBuilder query = QueryBuilders.rangeQuery(field).gte(from).lte(to);
        builder.filter(query);
    }

    public static void greaterThanOrEqual(String field, Object value, BoolQueryBuilder builder) {
        QueryBuilder query = QueryBuilders.rangeQuery(field).gte(value);
        builder.filter(query);
    }

    public static void lessThanOrEqual(String field, Object value, BoolQueryBuilder builder) {
        QueryBuilder query = QueryBuilders.rangeQuery(field).lte(value);
        builder.filter(query);
    }
}
