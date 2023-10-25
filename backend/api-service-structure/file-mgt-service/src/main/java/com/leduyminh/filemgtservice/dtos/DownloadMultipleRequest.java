package com.leduyminh.filemgtservice.dtos;

import com.leduyminh.gridfs.dtos.FileEntryDTO;
import lombok.Data;

import java.util.List;

@Data
public class DownloadMultipleRequest {
    private String databaseKey;
    private List<FileEntryDTO> fileList;
}
