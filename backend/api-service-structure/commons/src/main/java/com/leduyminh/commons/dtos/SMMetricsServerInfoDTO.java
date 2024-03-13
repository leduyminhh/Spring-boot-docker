package com.leduyminh.commons.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SMMetricsServerInfoDTO {
    private Long id;
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
	private String errorMongoDB;

	public SMMetricsServerInfoDTO(String server, Integer thread, Integer process, String cpuUsage, String cpuAvailable,
                                  String memoryTotal, String memoryInUse, String memoryAvailable, String diskInUse, String diskAvailable,
                                  String networkSend, String networkReceive, Boolean isMaster, String errorMongoDB) {
		super();
		this.server = server;
		this.thread = thread;
		this.process = process;
		this.cpuUsage = cpuUsage;
		this.cpuAvailable = cpuAvailable;
		this.memoryTotal = memoryTotal;
		this.memoryInUse = memoryInUse;
		this.memoryAvailable = memoryAvailable;
		this.diskInUse = diskInUse;
		this.diskAvailable = diskAvailable;
		this.networkSend = networkSend;
		this.networkReceive = networkReceive;
		this.isMaster = isMaster;
		this.errorMongoDB = errorMongoDB;
	}
    
}
