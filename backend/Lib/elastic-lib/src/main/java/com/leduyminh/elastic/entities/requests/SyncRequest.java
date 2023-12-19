package com.leduyminh.elastic.entities.requests;

import com.leduyminh.elastic.entities.ElasticConfig;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.elasticsearch.action.DocWriteRequest;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SyncRequest {

    private String collection;

    private String fieldPK;

    private Object data;

    private ElasticConfig elasticConfig;

    private DocWriteRequest.OpType mode; // operation mode
}
