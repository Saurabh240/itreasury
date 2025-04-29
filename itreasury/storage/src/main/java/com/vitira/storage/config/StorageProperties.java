package com.vitira.storage.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.unit.DataSize;

import java.util.Set;

@Data
@Component
@ConfigurationProperties(prefix = "app.storage")
public class StorageProperties {
    private DataSize maxFileSize = DataSize.ofMegabytes(10);
    private Set<String> allowedFileTypes = Set.of("pdf", "doc", "docx", "xls", "xlsx", "jpg", "jpeg", "png");
    private String uploadPathPrefix = "uploads";
    private String type = "aws";
} 