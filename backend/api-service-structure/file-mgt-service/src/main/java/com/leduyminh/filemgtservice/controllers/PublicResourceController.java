package com.leduyminh.filemgtservice.controllers;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/public-resource")
public class PublicResourceController {
    @Value("${public.path.user.guide}")
    private String userGuidePath;

    @GetMapping(value = "/user-guide", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity downloadFile() throws IOException {
        File file = new File(userGuidePath);
        InputStream inputStream = new FileInputStream(file);
        byte[] content = IOUtils.toByteArray(inputStream);
        HttpHeaders httpHeader = new HttpHeaders();
        httpHeader.setContentLength(content.length);
        httpHeader.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        httpHeader.set("Content-Disposition", "attachment;");
        return ResponseEntity.ok()
                .headers(httpHeader)
                .body(new ByteArrayResource(content));
    }

}
