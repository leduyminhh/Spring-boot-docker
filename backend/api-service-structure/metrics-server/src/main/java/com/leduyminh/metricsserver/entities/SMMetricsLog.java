package com.leduyminh.metricsserver.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "SM_Metrics_Log")
@NoArgsConstructor
@Data
@EntityListeners(AuditingEntityListener.class)
public class SMMetricsLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "Server")
    private String server;

    @Column(name = "Status")
    private String status;

    @Column(name = "Content", columnDefinition = "NVARCHAR(MAX)")
    private String content;

    @Column(name = "ErrorMessage")
    private String errorMessage;

    @Column(name = "CreatedDate", updatable = false)
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    protected Date createdDate;
}
