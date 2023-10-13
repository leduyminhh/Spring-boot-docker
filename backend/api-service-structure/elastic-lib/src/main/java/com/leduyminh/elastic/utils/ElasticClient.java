package com.leduyminh.elastic.utils;

import com.leduyminh.elastic.entities.ElasticConfig;
import com.leduyminh.elastic.exception.ElasticException;
import com.leduyminh.elastic.handler.ElasticHandler;
import lombok.Builder;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.CountRequest;
import org.elasticsearch.client.core.CountResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Builder
public class ElasticClient implements ElasticHandler {
    protected final ElasticConfig config;

    protected RestHighLevelClient instance;

    private Date exprie;

    @Override
    public RestHighLevelClient getInstance() {
        if (instance == null) {
            instance = createElasticClient();
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MINUTE, 5);
            exprie = cal.getTime();
        } else if (instance != null && exprie.getTime() < Calendar.getInstance().getTimeInMillis()) {
            try {
                instance.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            instance = createElasticClient();
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MINUTE, 5);
            exprie = cal.getTime();
        }
        return instance;
    }

    @Override
    public RestHighLevelClient createElasticClient() {
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(config.getUsername(), config.getPassword());
        credentialsProvider.setCredentials(AuthScope.ANY, credentials);

        String[] hosts = config.getHostNames().split(",");
        HttpHost[] httpHosts = new HttpHost[hosts.length];
        for (int index = 0; index < hosts.length; index++) {
            httpHosts[index] = HttpHost.create(hosts[index]);
        }
        RestClientBuilder restClientBuilder = RestClient.builder(httpHosts)
                .setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder
                        .setDefaultCredentialsProvider(config.getAuthorization() ? credentialsProvider : null)
                        .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                )
                .setRequestConfigCallback(
                        requestConfigBuilder -> requestConfigBuilder
                                .setConnectTimeout(60000)
                                .setSocketTimeout(3600000)
                );
        return new RestHighLevelClient(restClientBuilder);
    }

    @Override
    public String getIndex(String collection) {
        String kebabCase = collection
                .replaceAll("_", "-")
                .replaceAll("([a-z])([A-Z]+)", "$1-$2")
                .toLowerCase();
        return String.join("-", config.getRootIndex(), kebabCase);
    }

    @Override
    public IndexRequest createIndexRequest(Map<String, Object> document, String indexName, String id) {
        // elastic does not allow "_id" as a field name
        if (document.containsKey("_id")) {
            document.put("id", document.get("_id"));
            document.remove("_id");
        }

        // create request
        return new IndexRequest(indexName)
                .source(document, XContentType.JSON)
                .id(id);
    }

    @Override
    public String getId(Map<String, Object> document, String fieldPK) throws ElasticException {
        return document.get(fieldPK).toString();
    }

    @Override
    public boolean bulk(List<Map<String, Object>> documents, String collection, String fieldKey) {
        try {
            String index = this.getIndex(collection);
            BulkRequest bulkRequest = new BulkRequest();
            documents.forEach(document -> bulkRequest.add(createIndexRequest(document, index, document.get(fieldKey).toString())));
            getInstance().bulk(bulkRequest, RequestOptions.DEFAULT);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }


    @Override
    public boolean delete(List<String> ids, String collection) throws ElasticException {
        try {
            BulkRequest request = new BulkRequest();
            String index = getIndex(collection);
            ids.forEach(id -> request.add(new DeleteRequest(index, id)));
            getInstance().bulk(request, RequestOptions.DEFAULT);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ElasticException(e.getMessage());
        }
    }

    public Boolean existsIndex(String indexName) throws IOException {
        return getInstance().indices().exists(new GetIndexRequest(indexName), RequestOptions.DEFAULT);
    }

    @Override
    public List<Map<String, Object>> search(String collection, SearchSourceBuilder query) {
        String index = this.getIndex(collection);
        SearchRequest request = new SearchRequest()
                .indices(index)
                .source(query);
        SearchResponse response = getList(request);
        if (response != null) {
            SearchHits result = response.getHits();
            Map<String, Object> total = new HashMap<>();

            // change to count api for indexes with over 10000 items
            CountRequest countRequest = new CountRequest()
                    .indices(index)
                    .query(query.query());
            total.put("_total", count(countRequest));

            List<Map<String, Object>> resultList = Arrays.stream(result.getHits())
                    .map(SearchHit::getSourceAsMap)
                    .collect(Collectors.toList());
            resultList.add(total);
            return resultList;
        }
        return null;
    }

    @Override
    public SearchResponse getList(SearchRequest request) {
        try {
            return getInstance().search(request, RequestOptions.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public long count(CountRequest request) {
        try {
            CountResponse response = getInstance().count(request, RequestOptions.DEFAULT);
            return response.getCount();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

}
