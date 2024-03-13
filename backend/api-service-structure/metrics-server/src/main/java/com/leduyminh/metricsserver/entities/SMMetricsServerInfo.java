package com.leduyminh.metricsserver.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
@Getter
@Setter
@Entity
@Table(name = "SM_Metrics_Server_Info")
//@Table(name = "sm_metrics_server_info")
public class SMMetricsServerInfo extends AuditableSQLSV {

    // Sql server format
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "Server")
    private String server;
    @Column(name = "Thread")
    private Integer thread;
    @Column(name = "Process")
    private Integer process;
    @Column(name = "CPUUsage")
    private String cpuUsage;
    @Column(name = "CPUAvailable")
    private String cpuAvailable;
    @Column(name = "MemoryTotal")
    private String memoryTotal;
    @Column(name = "MemoryInUse")
    private String memoryInUse;
    @Column(name = "MemoryAvailable")
    private String memoryAvailable;
    @Column(name = "DiskInUse")
    private String diskInUse;
    @Column(name = "DiskAvailable")
    private String diskAvailable;
    @Column(name = "NetworkSend")
    private String networkSend;
    @Column(name = "NetworkReceive")
    private String networkReceive;
    @Column(name = "IsMaster")
    private Boolean isMaster;

    // Normal
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    private String server;
//    private Integer thread;
//    private Integer process;
//    private String cpuUsage;
//    private String cpuAvailable;
//    private String memoryTotal;
//    private String memoryInUse;
//    private String memoryAvailable;
//    private String diskInUse;
//    private String diskAvailable;
//    private String networkSend;
//    private String networkReceive;
//    private Boolean isMaster;
//    @Column(columnDefinition = "TEXT")
//    private String errorMongoDB;
}
