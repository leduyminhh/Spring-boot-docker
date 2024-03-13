package com.leduyminh.metricsserver.service.inf;

import org.json.JSONObject;

public interface CommandLineService {
	JSONObject getMemory();
	JSONObject getNetwork();
	JSONObject getProcess();
}
