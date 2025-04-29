package com.vitira.itreasury.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "compliance_documents")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ComplianceDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private String filePath; // Path where the document is stored

    @Column(nullable = false)
    private String fileType; // e.g., PDF, DOCX, etc.

    @Column(nullable = false)
    private Long fileSize; // Size in bytes

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "compliance_record_id", nullable = false)
    private ComplianceRecord complianceRecord;

    @Column(nullable = false)
    private String uploadedBy;

    @Column(nullable = false)
    private LocalDateTime uploadedAt;

    @Column
    private String description; // Optional description of the document

    @PrePersist
    protected void onCreate() {
        uploadedAt = LocalDateTime.now();
    }
} 