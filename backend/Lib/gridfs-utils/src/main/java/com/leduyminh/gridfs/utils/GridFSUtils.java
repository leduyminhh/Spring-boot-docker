package com.leduyminh.gridfs.utils;

import com.amazonaws.util.IOUtils;
import com.leduyminh.gridfs.exceptions.NotFoundException;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.mongodb.client.gridfs.model.GridFSUploadOptions;
import lombok.Builder;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

@Builder
public class GridFSUtils {
    private HashMap<String, MongoDatabase> mongoInstanceNodes;
    public String mongoInstanceCurrentKey;

    public MongoDatabase getDatabase(String key) {
        MongoDatabase mongoDatabase = null;
        try {
            String keyNode = key;
            if (key == null || key.isEmpty()) {
                keyNode = mongoInstanceCurrentKey;
            }
            if (mongoInstanceNodes.containsKey(keyNode)) {
                mongoDatabase = mongoInstanceNodes.get(keyNode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mongoDatabase;
    }

    public GridFSFile getFile(String key, String contentId, String bucketName) {
        GridFSBucket gridFSBucket = GridFSBuckets.create(getDatabase(key), bucketName);
        return gridFSBucket.find(new Document("_id", new ObjectId(contentId))).first();
    }

    public void deleteFile(String key, String contentId, String bucketName) {
        GridFSBucket gridFSBucket = GridFSBuckets.create(getDatabase(key), bucketName);
        gridFSBucket.delete(new ObjectId(contentId));
    }

    public void deleteBucket(String key, String bucketName) {
        GridFSBucket gridFSBucket = GridFSBuckets.create(getDatabase(key), bucketName);
        gridFSBucket.drop();
    }

    public String uploadFile(String key, File file, Document metadata, String bucketName, String fileName) throws Exception {
        InputStream targetStream = new FileInputStream(file);
        GridFSBucket gridFSBucket = GridFSBuckets.create(getDatabase(key), bucketName);
        GridFSUploadOptions options = new GridFSUploadOptions().metadata(metadata);
        return gridFSBucket.uploadFromStream(fileName, targetStream, options).toString();
    }

    public String uploadFile(String key, InputStream targetStream, Document metadata, String bucketName, String fileName) throws Exception {
        GridFSBucket gridFSBucket = GridFSBuckets.create(getDatabase(key), bucketName);
        GridFSUploadOptions options = new GridFSUploadOptions().metadata(metadata);
        return gridFSBucket.uploadFromStream(fileName, targetStream, options).toString();
    }

    public void delete(String key, ObjectId contentId, String bucketName) throws Exception {
        GridFSBucket gridFSBucket = GridFSBuckets.create(getDatabase(key), bucketName);
        gridFSBucket.delete(contentId);
    }

    public void copyFile(String key, File file, String contentId, String bucketName) throws Exception {
        GridFSDownloadStream downloadStream = null;
        try {
            GridFSBucket gridFSBucket = GridFSBuckets.create(getDatabase(key), bucketName);
            downloadStream = gridFSBucket.openDownloadStream(new ObjectId(contentId));
            byte[] bytesToWriteTo = IOUtils.toByteArray(downloadStream);
            FileUtils.writeByteArrayToFile(file, bytesToWriteTo);
            downloadStream.close();
        } catch (Exception e) {
            if (downloadStream != null) {
                downloadStream.close();
            }
            throw new IOException(e.getMessage());
        }
    }

    public byte[] downloadFile(String key, String contentId, String bucketName) {
        GridFSDownloadStream downloadStream = null;
        try {
            GridFSBucket gridFSBucket = GridFSBuckets.create(getDatabase(key), bucketName);
            downloadStream = gridFSBucket.openDownloadStream(new ObjectId(contentId));
            byte[] bytesToWriteTo = IOUtils.toByteArray(downloadStream);
            downloadStream.close();
            return bytesToWriteTo;
        } catch (Exception e) {
            if (downloadStream != null) {
                downloadStream.close();
            }
            throw new NotFoundException("Không tìm thấy tệp");
        }
    }
}

