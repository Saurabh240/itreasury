package com.vitira.storage.config;

import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "app.storage.type", havingValue = "azure")
public class AzureConfig {
    @Value("${azure.storage.connection-string:}")
    private String connectionString;

    @Bean
    public BlobServiceClient blobServiceClient() {
        if (connectionString.isEmpty()) {
            throw new IllegalStateException("Azure storage connection string is required when using Azure storage type");
        }
        
        return new BlobServiceClientBuilder()
                .connectionString(connectionString)
                .buildClient();
    }
} 