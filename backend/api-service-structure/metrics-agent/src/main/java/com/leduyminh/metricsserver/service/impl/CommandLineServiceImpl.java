package com.leduyminh.metricsserver.service.impl;

import com.leduyminh.metricsserver.service.inf.CommandLineService;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Service
public class CommandLineServiceImpl implements CommandLineService {
	
	@Override
	public JSONObject getMemory() {
		JSONObject js = new JSONObject();
		try {
			boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");
			if (isWindows) {
				ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c",
						"systeminfo | findstr /C:\"Total Physical Memory\" && dir");
				builder.redirectErrorStream(true);
				Process p = builder.start();
				BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
				String totalMemory = r.readLine();
				
				if (totalMemory != null && !totalMemory.equals("")) {
					totalMemory = totalMemory.split(":")[1].replaceAll("MB", "").replace(',', '.').trim();
					totalMemory = Math.round(Double.parseDouble(totalMemory) * 100) / 100 + "";
				}
				js.put("total", totalMemory + "G");
				
				builder = new ProcessBuilder("cmd.exe", "/c",
						"systeminfo | findstr /C:\"Available Physical Memory\" && dir");
				builder.redirectErrorStream(true);
				p = builder.start();
				r = new BufferedReader(new InputStreamReader(p.getInputStream()));
				String freeMemory = r.readLine();
				
				if (freeMemory != null && !freeMemory.equals("")) {
					freeMemory = freeMemory.split(":")[1].replaceAll("MB", "").replace(',', '.').trim();
					freeMemory = Math.round(Double.parseDouble(freeMemory) * 100) / 100 + "";
				}
				js.put("free", freeMemory + "G");
				
				String memoryInUse = Double.parseDouble(totalMemory) - Double.parseDouble(freeMemory) + "";
				js.put("used", Math.round(Double.parseDouble(memoryInUse) * 100) / 100 + "G");
				
				return js;
				
			} else {
					ProcessBuilder builder = new ProcessBuilder("sh", "-c", "free -g");
				builder.redirectErrorStream(true);
				Process p = builder.start();
				BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));

				String line;
				line = r.readLine();
				line = r.readLine();
				
				char[] charLine = line.toCharArray();
				StringBuilder stringBuilder = new StringBuilder();
				for (int i = 0; i < charLine.length; ++i) {
					if (charLine[i] != ' ') {
						stringBuilder.append(charLine[i]);
					} else if ((i + 1) < charLine.length && charLine[i + 1] != ' ') {
						stringBuilder.append(",");
					}
				}
				String[] lstMemory = stringBuilder.toString().split(",");
				int total = (int) Math.round(Double.parseDouble(lstMemory[1]) * 100) / 100;
				double used = Math.round(Double.parseDouble(lstMemory[2]) * 100.0) / 100.0;
				double free = Math.round(Double.parseDouble(lstMemory[3]) * 100.0) / 100.0;

				js.put("total", total + "G");
				js.put("used", used + "G");
				js.put("free", total - used + "G");
				js.put("available", total - used + "G");
				return js;
			}
		} catch (Exception e) {
			e.printStackTrace();
			js.put("message", e.getMessage());
			return js;
		}
	}
	
	@Override
	public JSONObject getNetwork() {
		JSONObject js = new JSONObject();
		try {
			boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");
			if (isWindows) {
				ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c",
						"netstat -ps ICMP | findstr /C:\"Messages\" && dir");
				builder.redirectErrorStream(true);
				Process p = builder.start();
				BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
				
				String line;
				line = r.readLine();
				
				char[] charLine = line.toCharArray();
				StringBuilder stringBuilder = new StringBuilder();
				for (int i = 0; i < charLine.length; ++i) {
					if (charLine[i] != ' ') {
						stringBuilder.append(charLine[i]);
					} else if ((i + 1) < charLine.length && charLine[i + 1] != ' ') {
						stringBuilder.append(",");
					}
				}
				String[] lstNetwork = stringBuilder.toString().split(",");
				
				js.put("receive", lstNetwork[2]);
				js.put("send", lstNetwork[3]);
				return js;
				
			} else {
				ProcessBuilder builder = new ProcessBuilder("sh", "-c", "netstat -s | grep receive");
				builder.redirectErrorStream(true);
				Process p = builder.start();
				BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));

				String line;
				line = r.readLine().trim();
				System.out.println("Network receive: "+line);
				
				String[] lstNetwork = line.toString().split(" ");
				js.put("receive",  lstNetwork[0].replace("sh:",""));
				
				builder = new ProcessBuilder("sh", "-c", "netstat -s | grep sent");
				builder.redirectErrorStream(true);
				p = builder.start();
				r = new BufferedReader(new InputStreamReader(p.getInputStream()));

				line = r.readLine().trim();
				System.out.println("Network sent: "+line);
				
				lstNetwork = line.toString().split(" ");
				js.put("send",  lstNetwork[0].replace("sh:",""));
				return js;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return js;
		}
	}

	@Override
	public JSONObject getProcess() {
		JSONObject js = new JSONObject();
		try {
			boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");
			if (isWindows) {
				
				js.put("process", 0);
				return js;
				
			} else {
				ProcessBuilder builder = new ProcessBuilder("sh", "-c", "ps -e | wc -l");
				builder.redirectErrorStream(true);
				Process p = builder.start();
				BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));

				String line = r.readLine().trim();
				
				js.put("process", Integer.parseInt(line));
				return js;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return js;
		}
	}

	private double getDataFromString(String input) {
		if (input.indexOf("Mi") > 0 || input.indexOf("M") > 0) {
			return Double.parseDouble(input.replaceAll("[^0-9]", "")) / 1024;
		}
		if (input.indexOf("Gi") > 0 || input.indexOf("G") > 0) {
			return Double.parseDouble(input.replaceAll("[^0-9]", ""));
		}

		return 0;
	}

}
