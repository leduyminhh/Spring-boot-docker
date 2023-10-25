package com.leduyminh.commons.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileMetadata {
    private String fileId;
    private String fileName;
    private String extension;
    private Date uploadDate;
    private Long fileSize; // bytes
}
