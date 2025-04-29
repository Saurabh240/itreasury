package com.vitira.storage.config;

import com.vitira.storage.service.StorageService;
import com.vitira.storage.service.impl.AzureBlobStorageService;
import com.vitira.storage.service.impl.LocalFileSystemStorageService;
import com.vitira.storage.service.impl.S3StorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class StorageConfig {
    @Value("${app.storage.type:local}")
    private String storageType;

    @Bean
    @Primary
    @Conditional(StorageCondition.class)
    public StorageService storageService(
            LocalFileSystemStorageService localFileSystemStorageService,
            S3StorageService s3StorageService,
            AzureBlobStorageService azureBlobStorageService) {
        return switch (storageType.toLowerCase()) {
            case "aws" -> s3StorageService;
            case "azure" -> azureBlobStorageService;
            case "local" -> localFileSystemStorageService;
            default -> throw new IllegalArgumentException("Unsupported storage type: " + storageType);
        };
    }
} 