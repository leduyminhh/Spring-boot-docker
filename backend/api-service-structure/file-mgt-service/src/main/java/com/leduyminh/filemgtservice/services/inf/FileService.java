package com.leduyminh.filemgtservice.services.inf;

import com.leduyminh.commons.dtos.FileMetadata;
import com.leduyminh.gridfs.dtos.FileEntryDTO;
import com.leduyminh.gridfs.dtos.FileRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

public interface FileService {
    FileEntryDTO uploadFile(MultipartFile file, String metadata, String key, String databaseKey) throws Exception;

    String uploadStream(File file, FileEntryDTO fileEntry, String databaseKey) throws Exception;

    byte[] downloadFile(String folder, String fileId, String databaseKey) throws Exception;

    byte[] downloadMultiple(List<FileEntryDTO> fileList, String databaseKey) throws Exception;

    String moveFile(FileRequest request) throws Exception;

    void deleteFiles(List<FileEntryDTO> fileList, String databaseKey) throws Exception;

    FileMetadata getMetadata(FileRequest request);

    FileEntryDTO uploadFileSign(MultipartFile multipartFile, String metadata, String fileName) throws Exception;
}
