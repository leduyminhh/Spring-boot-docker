package com.leduyminh.elastic.handler;

import com.leduyminh.elastic.exception.ElasticException;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.CountRequest;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.util.List;
import java.util.Map;

public interface ElasticHandler {
    RestHighLevelClient getInstance() throws ElasticException;

    RestHighLevelClient createElasticClient() throws ElasticException;

    IndexRequest createIndexRequest(Map<String, Object> document, String indexName, String id) throws ElasticException;

    String getId(Map<String, Object> document, String fieldPK) throws ElasticException;

    String getIndex(String collection) throws ElasticException;


    boolean bulk(List<Map<String, Object>> document, String collection, String fieldKey);

    boolean delete(List<String> ids, String collection) throws ElasticException;

    List<Map<String, Object>> search(String collection, SearchSourceBuilder query);

    SearchResponse getList(SearchRequest request);

    long count(CountRequest request);
}
