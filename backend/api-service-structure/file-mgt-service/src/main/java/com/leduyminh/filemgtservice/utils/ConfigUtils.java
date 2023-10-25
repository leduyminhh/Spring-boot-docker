package com.leduyminh.filemgtservice.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leduyminh.filemgtservice.dtos.SystemConfigFileDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class ConfigUtils {

    @Value("${config.default.accept.file.types}")
    private String acceptFileTypes;

    @Value("${config.default.min.files}")
    private Integer minFiles;

    @Value("${config.default.max.files}")
    private Integer maxFiles;

    @Value("${config.default.max.size}")
    private Integer maxSize;

    public SystemConfigFileDTO getFileConfig(RedisTemplate redisTemplate, String key) throws JsonProcessingException {
        Object fileConfig = redisTemplate.opsForValue().get(key);
        if (fileConfig != null) {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(fileConfig.toString(), SystemConfigFileDTO.class);
        }
        return SystemConfigFileDTO.builder()
                .acceptFileTypes(acceptFileTypes)
                .minFiles(minFiles)
                .maxFiles(maxFiles)
                .maxSize(maxSize)
                .build();
    }

}
