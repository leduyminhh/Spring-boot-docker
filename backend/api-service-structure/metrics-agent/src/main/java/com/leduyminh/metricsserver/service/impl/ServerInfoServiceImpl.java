package com.leduyminh.metricsserver.service.impl;

import com.leduyminh.metricsserver.service.inf.CommandLineService;
import com.leduyminh.metricsserver.service.inf.ServerInfoService;
import com.leduyminh.metricsserver.utils.MetricServiceUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Date;
import java.util.Enumeration;

@Service
public class ServerInfoServiceImpl implements ServerInfoService {
	
	@Autowired
	CommandLineService commandLineService;

	@Autowired
	Environment environment;

	@Autowired
    MetricServiceUtils metricServiceUtils;

	@Override
	public JSONObject updateServerInfo() {
		try {
			RestTemplate restTemplate = new RestTemplate();
			
			// get PROCESS
			JSONObject jsProcess = commandLineService.getProcess();
			int process = jsProcess.getInt("process");
			
			// get THREAD
			String uriSystemThread = "http://localhost:" + environment.getProperty("server.port") + environment.getProperty("server.servlet.context-path")  +"/actuator/metrics/jvm.threads.live";
			String resultSystemThread = restTemplate.getForObject(uriSystemThread, String.class);
			JSONObject jsSystemThread = new JSONObject(resultSystemThread);
			JSONArray measurements = jsSystemThread.getJSONArray("measurements");
			int threadLive = new JSONObject(measurements.get(0).toString()).getInt("value");
			
			// get DISK SPACE
			String uriDiskSpace = "http://localhost:" + environment.getProperty("server.port") + environment.getProperty("server.servlet.context-path")  +"/actuator/health/diskSpace";
			String resultDiskSpace = restTemplate.getForObject(uriDiskSpace, String.class);
			JSONObject jsDiskSpace = new JSONObject(resultDiskSpace);
			JSONObject details = jsDiskSpace.getJSONObject("details");
			String totalSpace = String.valueOf((double) (details.getDouble("total")/1024/1024/1024)); //convert byte to GB
			String freeSpace = String.valueOf((double) (details.getDouble("free")/1024/1024/1024)); //convert byte to GB
			String diskInUse = Double.parseDouble(totalSpace) - Double.parseDouble(freeSpace) + "";
			
			freeSpace = Math.round(Double.parseDouble(freeSpace) * 10) / 10 + "G";
			diskInUse = Math.round(Double.parseDouble(diskInUse) * 10) / 10 + "G";
			
			// get CPU
			String uriSystemCpu = "http://localhost:" + environment.getProperty("server.port") + environment.getProperty("server.servlet.context-path")  +"/actuator/metrics/system.cpu.usage";
			String resultSystemCpu = restTemplate.getForObject(uriSystemCpu, String.class);
			JSONObject jsSystemCpu = new JSONObject(resultSystemCpu);
			measurements = jsSystemCpu.getJSONArray("measurements");
			String cpuUsage = String.valueOf(Math.ceil( new JSONObject( measurements.get(0).toString()).getDouble("value") * 100) );
			String cpuAvailable = 100 - Double.parseDouble(cpuUsage) + "";
			
			cpuUsage = Math.round(Double.parseDouble(cpuUsage)) + "%";
			cpuAvailable = Math.round(Double.parseDouble(cpuAvailable)) + "%";
			
			//get RAM
			JSONObject jsMemory = commandLineService.getMemory();
			String totalMemory = jsMemory.getString("total");
			String freeMemory = jsMemory.getString("free");
			String memoryInUse = jsMemory.getString("used");
			
			// get NETWORK
			JSONObject jsNetWord = commandLineService.getNetwork();
			String networkSend = jsNetWord.has("send") ? jsNetWord.getString("send") : "0";
			String networkReceive = jsNetWord.has("receive") ? jsNetWord.getString("receive") : "0";
			String ip = environment.getProperty("server.listen.ip");
			String url = ip != null && !ip.isEmpty() ?  ip :  InetAddress.getLocalHost().getHostAddress();

//			String primaryMongoDB = "";
//			String secondaryMongoDB = "";
			String errorMongoDB = "";
			boolean isMaster = false;
//			if (environment.getProperty("spring.datasource.mongoDB.enable") != null && environment.getProperty("spring.datasource.mongoDB.enable").equals("true"))
//			{
//				Map<String, String> output = checkPrimaryOrSecondaryMongoDB();
//				if (output.get("error").equals(""))
//				{
//					isMaster = Boolean.parseBoolean(output.get("isMaster"));
//				}
//				else
//				{
//					errorMongoDB =output.get("error");
//				}
//			}

			SMMetricsServerInfoDTO metricInfoDTO = new SMMetricsServerInfoDTO(url, threadLive, process,
					cpuUsage, cpuAvailable, totalMemory, memoryInUse, freeMemory, diskInUse, freeSpace, networkSend, networkReceive, isMaster, errorMongoDB);


			System.out.println("*************** Start call api ***************" + new Date());
			metricServiceUtils.createMetric(metricInfoDTO);
			
			JSONObject dmserJsonObject = new JSONObject();
			return dmserJsonObject;
			
		} catch (Exception e) {
			e.printStackTrace();
			JSONObject dmserJsonObject = new JSONObject();
			dmserJsonObject.put("Exception", e.getMessage());
			return dmserJsonObject;
		}

	}

	private static InetAddress getLocalAddress(){
		try {
			Enumeration<NetworkInterface> b = NetworkInterface.getNetworkInterfaces();
			while( b.hasMoreElements()){
				for ( InterfaceAddress f : b.nextElement().getInterfaceAddresses())
					if ( f.getAddress().isSiteLocalAddress())
						return f.getAddress();
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}
		return null;
	}

//	@Override
//	public Map<String, String> checkPrimaryOrSecondaryMongoDB() {
//		Map<String, String> output = new HashMap<>();
//		try {
//			DatabaseRequestDTO mongoDB = new DatabaseRequestDTO();
//			mongoDB.setType(MONGODB);
//			mongoDB.setUrl(environment.getProperty("spring.datasource.mongoDB.url"));
//			mongoDB.setPort(environment.getProperty("spring.datasource.mongoDB.port"));
////			return DatabaseUtils.checkPrimaryOrSecondary(mongoDB);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return output;
//	}
}
