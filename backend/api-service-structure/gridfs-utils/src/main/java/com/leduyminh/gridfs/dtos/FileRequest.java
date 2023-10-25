package com.leduyminh.gridfs.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileRequest {
    private String databaseKey;
    private String currentFolder;
    private String newFolder;
    private String fileId;
    private String folder;
    private FileEntryDTO fileDTO;
    private Document newMetadata;
}
