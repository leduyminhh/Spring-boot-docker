package com.leduyminh.metricsserver.job;

import com.leduyminh.metricsserver.service.inf.ServerInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ServerStatementTask {
	@Autowired
	ServerInfoService serverInfoService;

//	@Value("${scheduleTime}")
//	private String time;
	@Scheduled(fixedRateString  = "${scheduleTime}")
    public void pingToServergetInfor() {
    	System.out.println("*************** Start get Server Info ***************" + new Date());
        try {
        	serverInfoService.updateServerInfo();
        } catch (Exception e) {
        	e.printStackTrace();
        }
    }
}
