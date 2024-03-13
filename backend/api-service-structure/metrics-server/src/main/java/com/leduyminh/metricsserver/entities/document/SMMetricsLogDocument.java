package com.leduyminh.metricsserver.entities.document;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.util.Date;

@Document("SMMetricsLog")
@NoArgsConstructor
@Data
@EntityListeners(AuditingEntityListener.class)
public class SMMetricsLogDocument {

    @Id
    private String id;

    private String server;

    private String status;

    private String content;

    private String errorMessage;

    protected Date createdDate;
    protected Date lastModifiedDate;
}
