package com.leduyminh.filemgtservice.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SystemConfigFileDTO {
    private String acceptFileTypes;
    private long minFiles;
    private long maxSize;
    private int maxFiles;
}
