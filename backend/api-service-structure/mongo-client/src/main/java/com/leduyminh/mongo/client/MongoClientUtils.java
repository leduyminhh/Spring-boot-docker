package com.leduyminh.mongo.client;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSUploadOptions;
import lombok.Builder;
import org.apache.commons.io.IOUtils;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.types.ObjectId;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.*;
import java.util.Date;
import java.util.Map;

@Builder
public class MongoClientUtils {
    public static CodecRegistry pojoCodecRegistry = org.bson.codecs.configuration.CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), org.bson.codecs.configuration.CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()));
    private MongoClient mongoClient;
    private Map<String, MongoDatabase> mongoDatabase;
    private RedisTemplate redisTemplate;
    private String configKey;
    private String uri;


    public MongoClient getInstantiate() {
        if (mongoClient == null) {
            try {
                mongoClient = MongoClients.create(getConfig());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return mongoClient;
    }

    public MongoDatabase getMongoDatabase(String key) {
        if (mongoDatabase.containsKey(key)) {
            return mongoDatabase.get(key);
        } else {
            return null;
        }
    }

    public MongoClient reload() {
        try {
            mongoClient = MongoClients.create(getConfig());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mongoClient;
    }

    private String getConfig() {
        String mongoClientURI = "";
        try {
            Object configDB = redisTemplate.opsForValue().get(configKey);
            if (configDB != null) {
                mongoClientURI = configDB.toString();
            } else {
                System.err.println("Not found config mongodb in Redis");
                mongoClientURI = uri;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error get config mongoDB in Redis");
            mongoClientURI = uri;
        }
        return mongoClientURI;
    }

    public void getFileFromMongoDB(File file, MongoDatabase databaseMongoDB, String contentId, String type) throws Exception {
        GridFSDownloadStream downloadStream = null;
        try {
            GridFSBucket gridFSBucket = GridFSBuckets.create(databaseMongoDB, type);
            downloadStream = gridFSBucket.openDownloadStream(new ObjectId(contentId));
            byte[] bytesToWriteTo = IOUtils.toByteArray(downloadStream);
            org.apache.commons.io.FileUtils.writeByteArrayToFile(file, bytesToWriteTo);
            downloadStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            if (downloadStream != null) {
                downloadStream.close();
            }
            throw new IOException("Not write content file " + file.getAbsolutePath());
        }
    }

    public byte[] getByteFromMongoDB(MongoDatabase databaseMongoDB, String contentId, String type) throws Exception {
        GridFSDownloadStream downloadStream = null;
        try {
            GridFSBucket gridFSBucket = GridFSBuckets.create(databaseMongoDB, type);
            downloadStream = gridFSBucket.openDownloadStream(new ObjectId(contentId));
            byte[] bytesToWriteTo = IOUtils.toByteArray(downloadStream);
            downloadStream.close();
            return bytesToWriteTo;
        } catch (Exception e) {
            e.printStackTrace();
            if (downloadStream != null) {
                downloadStream.close();
            }
            throw new IOException("Not write content to byte[] with fiedId : " + contentId);
        }
    }

    public ObjectId uploadFileToMongoDB(MongoDatabase databaseMongoDB, File file, Document metadata, String bucketName, Integer chunkSize, String key) throws Exception {
        try {
            InputStream targetStream = new FileInputStream(file);
            GridFSBucket gridFSBucket = GridFSBuckets.create(databaseMongoDB, bucketName);
            GridFSUploadOptions options = new GridFSUploadOptions().chunkSizeBytes(chunkSize).metadata(metadata);
            return gridFSBucket.uploadFromStream(key, targetStream, options);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new IOException("Không thể lưu file lên mongodb");
        }
    }

    public ObjectId uploadFileToMongoDB(MongoDatabase databaseMongoDB, InputStream targetStream, Document metadata, String bucketName, Integer chunkSize, String key) throws Exception {
        try {
            GridFSBucket gridFSBucket = GridFSBuckets.create(databaseMongoDB, bucketName);
            GridFSUploadOptions options = new GridFSUploadOptions().chunkSizeBytes(chunkSize).metadata(metadata);
            return gridFSBucket.uploadFromStream(key, targetStream, options);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException("Không thể lưu file lên mongodb");
        }
    }

    public static String getStringDoc(Document doc, String name) {
        return doc.getString(name) != null ? doc.getString(name) : null;
    }

    public static Boolean getBooleanDoc(Document doc, String name) {
        return doc.getBoolean(name) != null ? doc.getBoolean(name) : false;
    }

    public static Long getLongDoc(Document doc, String name) {
        try {
            return Long.parseLong(parseNumberString(doc, name));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0L;
    }


    public static Integer getIntegerDoc(Document doc, String name) {
        try {
            return Integer.parseInt(parseNumberString(doc, name));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String parseNumberString(Document doc, String name) {
        try {
            String value = doc.containsKey(name) ? doc.get(name).toString() : "0";
            return !value.equals("0.0") ? value : "0";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "0";
    }

    public static Date getDateDoc(Document doc, String name) {
        return doc.getDate(name) != null ? doc.getDate(name) : null;
    }
}
