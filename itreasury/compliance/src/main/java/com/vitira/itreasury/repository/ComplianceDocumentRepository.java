package com.vitira.itreasury.repository;

import com.vitira.itreasury.entity.ComplianceDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComplianceDocumentRepository extends JpaRepository<ComplianceDocument, Long> {
} 