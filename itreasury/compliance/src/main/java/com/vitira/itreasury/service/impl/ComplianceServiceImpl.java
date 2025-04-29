package com.vitira.itreasury.service.impl;

import com.vitira.itreasury.dto.ComplianceRecordDTO;
import com.vitira.itreasury.dto.ComplianceRecordRequestDTO;
import com.vitira.itreasury.entity.ComplianceDocument;
import com.vitira.itreasury.entity.ComplianceRecord;
import com.vitira.itreasury.enums.Priority;
import com.vitira.itreasury.enums.Severity;
import com.vitira.itreasury.mapper.ComplianceRecordMapper;
import com.vitira.itreasury.repository.ComplianceRecordRepository;
import com.vitira.itreasury.service.ComplianceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ComplianceServiceImpl implements ComplianceService {

    private final ComplianceRecordRepository complianceRecordRepository;
    private final ComplianceRecordMapper complianceRecordMapper;

    @Autowired
    public ComplianceServiceImpl(ComplianceRecordRepository complianceRecordRepository,
                               ComplianceRecordMapper complianceRecordMapper) {
        this.complianceRecordRepository = complianceRecordRepository;
        this.complianceRecordMapper = complianceRecordMapper;
    }

    @Override
    @Transactional
    public ComplianceRecordDTO createComplianceRecord(ComplianceRecordRequestDTO requestDTO, String username) {
        ComplianceRecord record = complianceRecordMapper.toEntity(requestDTO);
        record.setCreatedBy(username);
        record.setLastModifiedBy(username);
        return complianceRecordMapper.toDTO(complianceRecordRepository.save(record));
    }

    @Override
    @Transactional
    public List<ComplianceRecordDTO> createComplianceRecords(List<ComplianceRecordRequestDTO> complianceRecords, String username) {
        return complianceRecords.stream()
                .map(requestDTO -> {
                    ComplianceRecord record = complianceRecordMapper.toEntity(requestDTO);
                    record.setCreatedBy(username);
                    record.setLastModifiedBy(username);
                    return complianceRecordMapper.toDTO(complianceRecordRepository.save(record));
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ComplianceRecordDTO updateComplianceRecord(Long id, ComplianceRecordRequestDTO requestDTO, String username) {
        ComplianceRecord existingRecord = complianceRecordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Compliance record not found"));

        // Update compliance record fields using builder pattern
        ComplianceRecord updatedRecord = ComplianceRecord.builder()
                .id(existingRecord.getId())
                .name(requestDTO.getName() != null ? requestDTO.getName() : existingRecord.getName())
                .description(requestDTO.getDescription() != null ? requestDTO.getDescription() : existingRecord.getDescription())
                .status(requestDTO.getStatus() != null ? requestDTO.getStatus() : existingRecord.getStatus())
                .complianceCategory(requestDTO.getComplianceCategory() != null ? requestDTO.getComplianceCategory() : existingRecord.getComplianceCategory())
                .complianceFrequency(requestDTO.getComplianceFrequency() != null ? requestDTO.getComplianceFrequency() : existingRecord.getComplianceFrequency())
                .severity(requestDTO.getSeverity() != null ? requestDTO.getSeverity() : existingRecord.getSeverity())
                .priority(requestDTO.getPriority() != null ? requestDTO.getPriority() : existingRecord.getPriority())
                .isComplied(requestDTO.isComplied() ? requestDTO.isComplied() : existingRecord.isComplied())
                .dueDate(requestDTO.getDueDate() != null ? requestDTO.getDueDate() : existingRecord.getDueDate())
                .completionDate(requestDTO.getCompletionDate() != null ? requestDTO.getCompletionDate() : existingRecord.getCompletionDate())
                .assignedTo(requestDTO.getAssignedTo() != null ? requestDTO.getAssignedTo() : existingRecord.getAssignedTo())
                .notes(requestDTO.getNotes() != null ? requestDTO.getNotes() : existingRecord.getNotes())
                .createdBy(existingRecord.getCreatedBy())
                .createdAt(existingRecord.getCreatedAt())
                .lastModifiedBy(username)
                .build();

        // Handle document updates
        if (requestDTO.getDocuments() != null && !requestDTO.getDocuments().isEmpty()) {
            List<ComplianceDocument> updatedDocuments = new ArrayList<>();
            
            // Process existing documents
            requestDTO.getDocuments().stream()
                .filter(docDTO -> docDTO.getId() != null)
                .forEach(docDTO -> {
                    ComplianceDocument existingDoc = existingRecord.getDocuments().stream()
                        .filter(d -> d.getId().equals(docDTO.getId()))
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("Document not found: " + docDTO.getId()));

                    ComplianceDocument updatedDoc = ComplianceDocument.builder()
                        .id(existingDoc.getId())
                        .fileName(docDTO.getFileName() != null ? docDTO.getFileName() : existingDoc.getFileName())
                        .fileType(docDTO.getFileType() != null ? docDTO.getFileType() : existingDoc.getFileType())
                        .fileSize(docDTO.getFileSize() != null ? docDTO.getFileSize() : existingDoc.getFileSize())
                        .filePath(docDTO.getFilePath() != null ? docDTO.getFilePath() : existingDoc.getFilePath())
                        .description(docDTO.getDescription() != null ? docDTO.getDescription() : existingDoc.getDescription())
                        .complianceRecord(updatedRecord)
                        .build();

                    updatedDocuments.add(updatedDoc);
                });

            // Add new documents
            requestDTO.getDocuments().stream()
                .filter(docDTO -> docDTO.getId() == null)
                .forEach(docDTO -> {
                    ComplianceDocument newDoc = ComplianceDocument.builder()
                        .fileName(docDTO.getFileName())
                        .fileType(docDTO.getFileType())
                        .fileSize(docDTO.getFileSize())
                        .filePath(docDTO.getFilePath())
                        .description(docDTO.getDescription())
                        .complianceRecord(updatedRecord)
                        .uploadedBy(username)
                        .build();

                    updatedDocuments.add(newDoc);
                });

            updatedRecord.setDocuments(updatedDocuments);
        } else {
            updatedRecord.setDocuments(existingRecord.getDocuments());
        }

        return complianceRecordMapper.toDTO(complianceRecordRepository.save(updatedRecord));
    }

    @Override
    public ComplianceRecordDTO getComplianceRecord(Long id) {
        return complianceRecordRepository.findById(id)
                .map(complianceRecordMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Compliance record not found"));
    }

    @Override
    public List<ComplianceRecordDTO> getAllComplianceRecords() {
        return complianceRecordRepository.findAll().stream()
                .map(complianceRecordMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteComplianceRecord(Long id) {
        complianceRecordRepository.deleteById(id);
    }

    @Override
    public List<ComplianceRecordDTO> getComplianceRecordsByStatus(String status) {
        return complianceRecordRepository.findByStatus(status).stream()
                .map(complianceRecordMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ComplianceRecordDTO> getComplianceRecordsByCategory(String category) {
        return complianceRecordRepository.findByComplianceCategory(category).stream()
                .map(complianceRecordMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ComplianceRecordDTO> getComplianceRecordsBySeverity(Severity severity) {
        return complianceRecordRepository.findBySeverity(severity).stream()
                .map(complianceRecordMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ComplianceRecordDTO> getComplianceRecordsByPriority(Priority priority) {
        return complianceRecordRepository.findByPriority(priority).stream()
                .map(complianceRecordMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ComplianceRecordDTO> getComplianceRecordsBySeverityAndPriority(Severity severity, Priority priority) {
        return complianceRecordRepository.findBySeverityAndPriority(severity, priority).stream()
                .map(complianceRecordMapper::toDTO)
                .collect(Collectors.toList());
    }
} 