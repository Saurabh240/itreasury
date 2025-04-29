package com.vitira.itreasury.service;

import com.vitira.itreasury.dto.ComplianceRecordDTO;
import com.vitira.itreasury.dto.ComplianceRecordRequestDTO;
import com.vitira.itreasury.enums.Priority;
import com.vitira.itreasury.enums.Severity;

import java.util.List;

public interface ComplianceService {
    ComplianceRecordDTO createComplianceRecord(ComplianceRecordRequestDTO requestDTO, String username);
    List<ComplianceRecordDTO> createComplianceRecords(List<ComplianceRecordRequestDTO> complianceRecords, String username);
    ComplianceRecordDTO updateComplianceRecord(Long id, ComplianceRecordRequestDTO requestDTO, String username);
    ComplianceRecordDTO getComplianceRecord(Long id);
    List<ComplianceRecordDTO> getAllComplianceRecords();
    void deleteComplianceRecord(Long id);
    List<ComplianceRecordDTO> getComplianceRecordsByStatus(String status);
    List<ComplianceRecordDTO> getComplianceRecordsByCategory(String category);
    List<ComplianceRecordDTO> getComplianceRecordsBySeverity(Severity severity);
    List<ComplianceRecordDTO> getComplianceRecordsByPriority(Priority priority);
    List<ComplianceRecordDTO> getComplianceRecordsBySeverityAndPriority(Severity severity, Priority priority);
} 