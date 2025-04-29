package com.vitira.itreasury.service;

import com.vitira.itreasury.entity.ComplianceDocument;
import com.vitira.itreasury.entity.ComplianceRecord;
import com.vitira.itreasury.repository.ComplianceDocumentRepository;
import com.vitira.storage.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class ComplianceDocumentService {
    private final ComplianceDocumentRepository complianceDocumentRepository;
    private final StorageService storageService;

    public ComplianceDocument uploadDocument(MultipartFile file, ComplianceRecord complianceRecord, String uploadedBy, String description) throws IOException {
        // Upload file to cloud storage
        String filePath = storageService.uploadFile(file, "compliance/" + complianceRecord.getId());

        // Create and save document metadata
        ComplianceDocument document = ComplianceDocument.builder()
                .fileName(file.getOriginalFilename())
                .filePath(filePath)
                .fileType(file.getContentType())
                .fileSize(file.getSize())
                .complianceRecord(complianceRecord)
                .uploadedBy(uploadedBy)
                .description(description)
                .build();

        return complianceDocumentRepository.save(document);
    }

    public InputStream downloadDocument(Long documentId) {
        ComplianceDocument document = complianceDocumentRepository.findById(documentId)
                .orElseThrow(() -> new RuntimeException("Document not found"));
        
        return storageService.downloadFile(document.getFilePath());
    }

    public void deleteDocument(Long documentId) throws IOException {
        ComplianceDocument document = complianceDocumentRepository.findById(documentId)
                .orElseThrow(() -> new RuntimeException("Document not found"));

        // Delete file from cloud storage
        storageService.deleteFile(document.getFilePath());

        // Delete from database
        complianceDocumentRepository.delete(document);
    }

    public String getDocumentUrl(Long documentId) {
        ComplianceDocument document = complianceDocumentRepository.findById(documentId)
                .orElseThrow(() -> new RuntimeException("Document not found"));
        
        return storageService.getFileUrl(document.getFilePath());
    }
} 