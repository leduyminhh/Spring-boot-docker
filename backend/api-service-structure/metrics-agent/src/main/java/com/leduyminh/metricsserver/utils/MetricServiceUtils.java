package com.leduyminh.metricsserver.utils;

import com.google.gson.Gson;
import org.json.JSONObject;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

public class MetricServiceUtils {
    private static Environment env;
    public MetricServiceUtils(Environment environment) {
        this.env = environment;
    }

    public static boolean createMetric(SMMetricsServerInfoDTO dto) {
        try {
            Map<String, String> headers = new HashMap<>();
            JSONObject body;

            String url = env.getProperty("metrics.url.create");
            headers.put("Content-Type", "application/json");

            String jsonString = new Gson().toJson(dto);
            body = new JSONObject(jsonString);

            Map<String, Object> resultHTTP = HttpClientUtil.HTTP_POST_BODY_CUSTOM(url, headers, body);
            if (resultHTTP != null) {
                Integer status = (Integer) resultHTTP.get("status");
                if (status != null && status == HttpStatus.OK.value()) {
                    String data = (String) resultHTTP.get("data");
                    return true;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

}
