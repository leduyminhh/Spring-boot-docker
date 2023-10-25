package com.leduyminh.filemgtservice.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.leduyminh.commons.dtos.FileMetadata;
import com.leduyminh.filemgtservice.exceptions.NotAcceptableException;
import com.leduyminh.filemgtservice.services.inf.FileService;
import com.leduyminh.filemgtservice.dtos.SystemConfigFileDTO;
import com.leduyminh.filemgtservice.exceptions.NotFoundException;
import com.leduyminh.filemgtservice.utils.ConfigUtils;
import com.leduyminh.gridfs.dtos.FileEntryDTO;
import com.leduyminh.gridfs.dtos.FileRequest;
import com.leduyminh.gridfs.utils.GridFSUtils;
import com.mongodb.client.gridfs.model.GridFSFile;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.bson.Document;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    private final GridFSUtils gridFSUtils;
    private final ConfigUtils configUtils;
    private final RedisTemplate redisTemplate;
    private final Environment evn;

    private File createTempFile() throws IOException {
        File file = File.createTempFile("vbdc-", ".tmp");
        file.deleteOnExit();
        return file;
    }

    private Boolean validFileType(String fileName, String key) throws JsonProcessingException {
        String extension = "";
        if (fileName.contains(".")) {
            extension += fileName.substring(fileName.lastIndexOf("."));
        }
        SystemConfigFileDTO fileConfig = configUtils.getFileConfig(redisTemplate, key);
        String acceptFileTypes = fileConfig.getAcceptFileTypes();
        String[] fileTypes = acceptFileTypes.split(",");
        String finalExtension = extension;
        return Arrays.stream(fileTypes).anyMatch(type -> {
            if (type.equals("image/*")) {
                return Arrays.asList(".png", ".jpg", ".jpeg", ".svg", ".gif", ".bmp", ".webp").contains(finalExtension);
            } else return type.equals(finalExtension);
        });
    }

    private Boolean validFileName(String fileName) {
        return Integer.valueOf(evn.getProperty("file.filename.length")) >= fileName.length();
    }

    @Override
    public FileEntryDTO uploadFile(MultipartFile multipartFile, String metadata, String key, String databaseKey) throws Exception {
        if  (multipartFile.isEmpty()) {
            throw new NotAcceptableException("message.file.invalid.size");
        }
        if (!this.validFileName(multipartFile.getOriginalFilename())) {
            throw new NotAcceptableException("message.file.invalid.name");
        }
        if (this.validFileType(multipartFile.getOriginalFilename(), key)) {
            File file = createTempFile();
            multipartFile.transferTo(file);
            Document meta = Document.parse(metadata);
            String fileId = gridFSUtils.uploadFile(databaseKey, file, meta, "temp", multipartFile.getOriginalFilename());

            return FileEntryDTO.builder()
                    .fileId(fileId)
                    .name(multipartFile.getOriginalFilename()).fileSize(multipartFile.getSize())
                    .folder("temp")
                    .extension("." + FilenameUtils.getExtension(multipartFile.getOriginalFilename()))
                    .build();
        } else {
            throw new NotAcceptableException("message.file.invalid.type");
        }
    }

    @Override
    public String uploadStream(File file, FileEntryDTO fileEntry, String databaseKey) throws Exception {
        Document metadata = Document.parse(fileEntry.getMetadata());
        return gridFSUtils.uploadFile(databaseKey, file, metadata, fileEntry.getFolder(), fileEntry.getName());
    }

    @Override
    public byte[] downloadFile(String folder, String fileId, String databaseKey) {
        return gridFSUtils.downloadFile(databaseKey, fileId, folder);
    }

    @Override
    public byte[] downloadMultiple(List<FileEntryDTO> fileList, String databaseKey) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(baos);
        for (FileEntryDTO file : fileList) {
            byte[] data = gridFSUtils.downloadFile(databaseKey, file.getFileId(), file.getFolder());
            ZipEntry zipEntry = new ZipEntry(file.getName());
            zos.putNextEntry(zipEntry);
            zos.write(data);
        }
        zos.finish();
        zos.flush();
        zos.closeEntry();
        return baos.toByteArray();
    }

    @Override
    public String moveFile(FileRequest request) throws Exception {
        String fileId = request.getFileId();
        String newFolder = request.getNewFolder();
        String currentFolder = request.getCurrentFolder();
        GridFSFile oldFile = gridFSUtils.getFile(request.getDatabaseKey(), fileId, currentFolder);
        Document metadata = request.getNewMetadata() != null ? request.getNewMetadata() : oldFile.getMetadata();

        // delete then copy file to new bucket
        File tempFile = createTempFile();
        gridFSUtils.copyFile(request.getDatabaseKey(), tempFile, fileId, currentFolder);
        gridFSUtils.deleteFile(request.getDatabaseKey(), fileId, currentFolder);
        return gridFSUtils.uploadFile(request.getDatabaseKey(), tempFile, metadata, newFolder, oldFile.getFilename());
    }

    @Override
    public void deleteFiles(List<FileEntryDTO> fileList, String databaseKey) {
        for (FileEntryDTO file : fileList) {
            GridFSFile oldFile = gridFSUtils.getFile(databaseKey, file.getFileId(), file.getFolder());
            if (oldFile != null) gridFSUtils.deleteFile(databaseKey, file.getFileId(), file.getFolder());
        }
    }

    @Override
    public FileEntryDTO uploadFileSign(MultipartFile multipartFile, String metadata, String fileName) throws Exception {
        File file = createTempFile();
        multipartFile.transferTo(file);
        Document meta = Document.parse(metadata);
        fileName = fileName.substring(0, fileName.lastIndexOf(".")) + "-sign.pdf";
        String fileId = gridFSUtils.uploadFile("current", file, meta, "temp", fileName);
        return FileEntryDTO.builder()
                .fileId(fileId)
                .name(fileName).fileSize(multipartFile.getSize())
                .folder("temp").extension(".pdf")
                .signFile(true)
                .build();
    }

    @Override
    public FileMetadata getMetadata(FileRequest request) {
        GridFSFile file = gridFSUtils.getFile(request.getDatabaseKey(), request.getFileId(), request.getFolder());
        if (file != null) {
            String fileName = file.getFilename();
            return FileMetadata.builder()
                    .fileId(request.getFileId())
                    .fileName(file.getFilename())
                    .fileSize(file.getLength())
                    .uploadDate(file.getUploadDate())
                    .extension(fileName.substring(fileName.lastIndexOf(".")))
                    .build();
        } else {
            throw new NotFoundException("message.file.not.found");
        }
    }
}
