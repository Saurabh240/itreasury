package com.vitira.storage.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.vitira.storage.config.StorageProperties;
import com.vitira.storage.exception.StorageException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
@ConditionalOnProperty(name = "app.storage.type", havingValue = "aws")
public class S3StorageService extends AbstractStorageService {
    private final AmazonS3 amazonS3;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    public S3StorageService(StorageProperties storageProperties, AmazonS3 amazonS3) {
        super(storageProperties);
        this.amazonS3 = amazonS3;
    }

    @Override
    public String uploadFile(MultipartFile file, String path) {
        validateFile(file);
        
        try {
            String fileName = generateUniqueFileName(file.getOriginalFilename());
            String key = storageProperties.getUploadPathPrefix() + "/" + path + "/" + fileName;

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            metadata.setContentType(file.getContentType());

            PutObjectRequest putObjectRequest = new PutObjectRequest(
                    bucketName,
                    key,
                    file.getInputStream(),
                    metadata
            );

            amazonS3.putObject(putObjectRequest);
            return key;
        } catch (IOException e) {
            throw new StorageException("Failed to upload file to S3", e);
        }
    }

    @Override
    public InputStream downloadFile(String path) {
        S3Object s3Object = amazonS3.getObject(bucketName, path);
        return s3Object.getObjectContent();
    }

    @Override
    public void deleteFile(String path) {
        amazonS3.deleteObject(bucketName, path);
    }

    @Override
    public String getFileUrl(String path) {
        return amazonS3.getUrl(bucketName, path).toString();
    }

    private String generateUniqueFileName(String originalFileName) {
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        return UUID.randomUUID().toString() + extension;
    }
} 