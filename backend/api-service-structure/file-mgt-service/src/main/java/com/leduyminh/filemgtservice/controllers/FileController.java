package com.leduyminh.filemgtservice.controllers;

import com.leduyminh.commons.dtos.DataResponse;
import com.leduyminh.commons.dtos.FileMetadata;
import com.leduyminh.commons.utils.BusinessCommon;
import com.leduyminh.filemgtservice.dtos.DownloadMultipleRequest;
import com.leduyminh.filemgtservice.services.inf.FileService;
import com.leduyminh.gridfs.dtos.FileEntryDTO;
import com.leduyminh.gridfs.dtos.FileRequest;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/")
public class FileController extends BaseController {
    private final FileService fileService;

    @PostMapping(value = "/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_OCTET_STREAM_VALUE})
    public ResponseEntity<DataResponse> uploadFile(@RequestParam("files") MultipartFile file,
                                                   @RequestParam("metadata") String metadata,
                                                   @RequestParam("key") String key, @RequestParam(name = "databaseKey",defaultValue = "current") String databaseKey) throws Exception {
        FileEntryDTO result = fileService.uploadFile(file, metadata, key, databaseKey);
        return BusinessCommon.createResponse(result, super.getMessage("message.action.success.commons"), HttpStatus.OK);
    }

    @PostMapping(value = "/upload/stream")
    public ResponseEntity<DataResponse> uploadStream(@RequestParam("file") File file,@RequestParam(name = "databaseKey",defaultValue = "current") String databaseKey,
                                                     @RequestBody FileEntryDTO fileEntry) throws Exception {
        String result = fileService.uploadStream(file, fileEntry, databaseKey);
        return BusinessCommon.createResponse(result, super.getMessage("message.action.success.commons"), HttpStatus.OK);
    }

    @PostMapping(value = "/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity downloadFile(@RequestBody FileRequest request) throws Exception {
        byte[] content = fileService.downloadFile(request.getFolder(), request.getFileId(), request.getDatabaseKey());
        HttpHeaders httpHeader = new HttpHeaders();
        httpHeader.setContentLength(content.length);
        httpHeader.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        httpHeader.set("Content-Disposition", "attachment;");
        return ResponseEntity.ok()
                .headers(httpHeader)
                .body(new ByteArrayResource(content));
    }

    @PostMapping(value = "/download/multiple", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity downloadMultiple(@RequestBody DownloadMultipleRequest request) throws Exception {
        byte[] content = fileService.downloadMultiple(request.getFileList(), request.getDatabaseKey());
        HttpHeaders httpHeader = new HttpHeaders();
        httpHeader.setContentLength(content.length);
        httpHeader.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        httpHeader.set("Content-Disposition", "attachment;");
        return ResponseEntity.ok()
                .headers(httpHeader)
                .body(new ByteArrayResource(content));
    }

    @PostMapping(value = "/download/stream", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ByteArrayResource downloadStream(@RequestBody FileRequest request) throws Exception {
        byte[] content = fileService.downloadFile(request.getCurrentFolder(), request.getFileId(), request.getDatabaseKey());
        return new ByteArrayResource(content);
    }

    @PostMapping(value = "/move")
    public ResponseEntity<DataResponse> moveFile(@RequestBody FileRequest request) throws Exception {
        String result = fileService.moveFile(request);
        return BusinessCommon.createResponse(result, super.getMessage("message.action.success.commons"), HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<DataResponse> deleteFiles(@RequestBody List<FileEntryDTO> fileList, @RequestParam(name = "databaseKey",defaultValue = "current")  String databaseKey) throws Exception {
        fileService.deleteFiles(fileList, databaseKey);
        return BusinessCommon.createResponse(null, super.getMessage("message.action.success.commons"), HttpStatus.OK);
    }

    @PostMapping(value = "/metadata")
    public ResponseEntity<DataResponse> getMetadata(@RequestBody FileRequest request) {
        FileMetadata result = fileService.getMetadata(request);
        return BusinessCommon.createResponse(result, super.getMessage("message.action.success.commons"), HttpStatus.OK);
    }

    @GetMapping(value = "/signature/download/{folder}/{fileId}")
    public ResponseEntity downloadFileSign(@PathVariable String folder, @PathVariable String fileId) throws Exception {
        byte[] content = fileService.downloadFile(folder, fileId, "current");
        HttpHeaders httpHeader = new HttpHeaders();
        httpHeader.setContentLength(content.length);
        httpHeader.setContentType(MediaType.APPLICATION_PDF);
        httpHeader.set("Content-Disposition", "attachment;");
        return ResponseEntity.ok()
            .headers(httpHeader)
            .body(new ByteArrayResource(content));
    }

    @PostMapping(value = "/signature/upload/{fileName}", produces = {"application/json"})
    public ResponseEntity<?> uploadFileSign(@RequestParam("uploadfile") MultipartFile file, @PathVariable String fileName) throws Exception{
        JSONObject responseCKS = new JSONObject();
        responseCKS.put("Status", true);
        responseCKS.put("Message", "");
        try {
            FileEntryDTO result = fileService.uploadFileSign(file, "{}", fileName);
            responseCKS.put("FileServer", result.toString());
//            responseCKS.put("bucket", "tepkyso");
//            responseCKS.put("Message", "");
//            responseCKS.put("FileName", result.getName());
            return new ResponseEntity<>(responseCKS.toString(), HttpStatus.OK);
        } catch (Exception e) {
            responseCKS.put("Status", false);
            responseCKS.put("Message", e.getMessage());
            throw e;
        }

    }

}
