package com.leduyminh.elastic.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;

import java.util.Map;

public class MapperUtils {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static String toJsonString(Object object) throws JsonProcessingException {
        return mapper.writeValueAsString(object);
    }

    public static JSONObject toJsonObject(Object object) throws JsonProcessingException {
        return new JSONObject(mapper.writeValueAsString(object));
    }

    public static Map<String, Object> toMap(Object object) throws JsonProcessingException {
        return new JSONObject(mapper.writeValueAsString(object)).toMap();
    }
}
