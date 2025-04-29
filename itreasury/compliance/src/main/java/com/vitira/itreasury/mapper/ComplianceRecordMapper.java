package com.vitira.itreasury.mapper;

import com.vitira.itreasury.dto.ComplianceRecordDTO;
import com.vitira.itreasury.dto.ComplianceRecordRequestDTO;
import com.vitira.itreasury.entity.ComplianceRecord;
import org.springframework.stereotype.Component;

@Component
public class ComplianceRecordMapper {
    
    public ComplianceRecord toEntity(ComplianceRecordRequestDTO dto) {
        return ComplianceRecord.builder()
                .name(dto.getName())
                .complianceCategory(dto.getComplianceCategory())
                .complianceFrequency(dto.getComplianceFrequency())
                .description(dto.getDescription())
                .status(dto.getStatus())
                .isComplied(dto.isComplied())
                .dueDate(dto.getDueDate())
                .completionDate(dto.getCompletionDate())
                .assignedTo(dto.getAssignedTo())
                .notes(dto.getNotes())
                .severity(dto.getSeverity())
                .priority(dto.getPriority())
                .documents(dto.getDocuments())
                .build();
    }

    public ComplianceRecordDTO toDTO(ComplianceRecord entity) {
        return ComplianceRecordDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .complianceCategory(entity.getComplianceCategory())
                .complianceFrequency(entity.getComplianceFrequency())
                .description(entity.getDescription())
                .status(entity.getStatus())
                .isComplied(entity.isComplied())
                .dueDate(entity.getDueDate())
                .completionDate(entity.getCompletionDate())
                .assignedTo(entity.getAssignedTo())
                .notes(entity.getNotes())
                .severity(entity.getSeverity())
                .priority(entity.getPriority())
                .documents(entity.getDocuments())
                .createdAt(entity.getCreatedAt())
                .createdBy(entity.getCreatedBy())
                .lastModifiedAt(entity.getLastModifiedAt())
                .lastModifiedBy(entity.getLastModifiedBy())
                .build();
    }
} 