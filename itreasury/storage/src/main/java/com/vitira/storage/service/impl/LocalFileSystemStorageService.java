package com.vitira.storage.service.impl;

import com.vitira.storage.config.StorageProperties;
import com.vitira.storage.exception.StorageException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;

@Service
@ConditionalOnProperty(name = "app.storage.type", havingValue = "local", matchIfMissing = true)
// @RequiredArgsConstructor
public class LocalFileSystemStorageService extends AbstractStorageService {
    @Value("${app.storage.local.base-dir:${user.home}/app-storage}")
    private String baseDir;

    public LocalFileSystemStorageService(StorageProperties storageProperties) {
        super(storageProperties);
    }

    @Override
    public String uploadFile(MultipartFile file, String path) {
        validateFile(file);
        
        try {
            String fileName = generateUniqueFileName(Objects.requireNonNull(file.getOriginalFilename()));
            String relativePath = storageProperties.getUploadPathPrefix() + "/" + path + "/" + fileName;
            Path targetPath = getStoragePath(relativePath);

            // Create parent directories if they don't exist
            Files.createDirectories(targetPath.getParent());

            // Save file
            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
            
            return relativePath;
        } catch (IOException e) {
            throw new StorageException("Failed to store file", e);
        }
    }

    @Override
    public InputStream downloadFile(String path) {
        try {
            Path filePath = getStoragePath(path);
            return Files.newInputStream(filePath);
        } catch (IOException e) {
            throw new StorageException("Failed to read file", e);
        }
    }

    @Override
    public void deleteFile(String path) {
        try {
            Path filePath = getStoragePath(path);
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            throw new StorageException("Failed to delete file", e);
        }
    }

    @Override
    public String getFileUrl(String path) {
        // For local storage, return a file:// URL
        return "file://" + getStoragePath(path).toString();
    }

    private Path getStoragePath(String relativePath) {
        return Paths.get(baseDir).resolve(relativePath);
    }

    private String generateUniqueFileName(String originalFileName) {
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        return UUID.randomUUID().toString() + extension;
    }
} 