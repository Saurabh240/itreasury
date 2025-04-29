package com.vitira.itreasury.repository;

import com.vitira.itreasury.entity.ComplianceRecord;
import com.vitira.itreasury.enums.Priority;
import com.vitira.itreasury.enums.Severity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComplianceRecordRepository extends JpaRepository<ComplianceRecord, Long> {
    List<ComplianceRecord> findByStatus(String status);
    List<ComplianceRecord> findByComplianceCategory(String category);
    List<ComplianceRecord> findBySeverity(Severity severity);
    List<ComplianceRecord> findByPriority(Priority priority);
    List<ComplianceRecord> findBySeverityAndPriority(Severity severity, Priority priority);
} 