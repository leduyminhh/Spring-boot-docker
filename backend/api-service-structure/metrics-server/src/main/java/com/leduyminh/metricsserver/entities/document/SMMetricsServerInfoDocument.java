package com.leduyminh.metricsserver.entities.document;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Document("SMMetricsServerInfo")
public class SMMetricsServerInfoDocument {

    @Id
    private String id;
    private String server;
    private Integer thread;
    private Integer process;
    private String cpuUsage;
    private String cpuAvailable;
    private String memoryTotal;
    private String memoryInUse;
    private String memoryAvailable;
    private String diskInUse;
    private String diskAvailable;
    private String networkSend;
    private String networkReceive;
    private Boolean isMaster;

    protected Date createdDate;
    protected Date lastModifiedDate;

}
