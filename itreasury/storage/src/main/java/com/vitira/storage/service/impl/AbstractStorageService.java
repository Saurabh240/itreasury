package com.vitira.storage.service.impl;

import com.vitira.storage.config.StorageProperties;
import com.vitira.storage.exception.StorageException;
import com.vitira.storage.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
public abstract class AbstractStorageService implements StorageService {
    protected final StorageProperties storageProperties;

    protected void validateFile(MultipartFile file) {
        validateFileSize(file);
        validateFileType(file);
    }

    private void validateFileSize(MultipartFile file) {
        if (file.getSize() > storageProperties.getMaxFileSize().toBytes()) {
            throw new StorageException(
                String.format("File size exceeds maximum allowed size of %s", 
                    storageProperties.getMaxFileSize().toString())
            );
        }
    }

    private void validateFileType(MultipartFile file) {
        String fileExtension = getFileExtension(file.getOriginalFilename());
        if (!storageProperties.getAllowedFileTypes().contains(fileExtension.toLowerCase())) {
            throw new StorageException(
                String.format("File type %s is not allowed. Allowed types are: %s",
                    fileExtension,
                    String.join(", ", storageProperties.getAllowedFileTypes()))
            );
        }
    }

    private String getFileExtension(String filename) {
        if (filename == null || filename.lastIndexOf(".") == -1) {
            return "";
        }
        return filename.substring(filename.lastIndexOf(".") + 1);
    }
} 