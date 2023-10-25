package com.leduyminh.gridfs.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileEntryDTO {
    private String name; //Ten duy nhat cua tap tin dinh kem - Dung de tranh trung ten
    private String docId;
    private String fileId;
    private String originName;
    private String extension;
    private String contentType;
    private String folder;
    private String metadata;
    private Long fileSize;

    private Boolean signFile;

    @Override
    public String toString() {
        return "{" +
                " \"name\":\"" + name + '\"' +
                ", \"docId\":\"" + docId + '\"' +
                ", \"fileId\":\"" + fileId + '\"' +
                ", \"originName\":\"" + originName + '\"' +
                ", \"extension\":\"" + extension + '\"' +
                ", \"contentType\":\"" + contentType + '\"' +
                ", \"folder\":\"" + folder + '\"' +
                ", \"metadata\":\"" + metadata + '\"' +
                ", \"fileSize\":\"" + fileSize + '\"' +
                ", \"signFile\":\"" + signFile + '\"' +
                '}';
    }
}
