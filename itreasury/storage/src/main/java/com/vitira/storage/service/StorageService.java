package com.vitira.storage.service;

import org.springframework.web.multipart.MultipartFile;
import java.io.InputStream;

public interface StorageService {
    String uploadFile(MultipartFile file, String path);
    InputStream downloadFile(String path);
    void deleteFile(String path);
    String getFileUrl(String path);
} 