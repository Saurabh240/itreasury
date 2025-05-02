package com.vitira.itreasury.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vitira.itreasury.enums.ComplianceFrequency;
import com.vitira.itreasury.enums.Priority;
import com.vitira.itreasury.enums.Severity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "compliances")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ComplianceRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Unique identifier for the compliance record

    @Column(nullable = false)
    private String name; // Compliance name (e.g., "Tax Filing", "ISO Certification")

    @Column(nullable = false)
    private String complianceCategory; // Category of compliance (e.g., "Tax", "Legal", "Security")

    @Column
    @Enumerated(EnumType.STRING)
    private ComplianceFrequency complianceFrequency; // Frequency of compliance (e.g., "Daily", "Weekly", "Monthly", "Quarterly", "Annually")

    @Column(nullable = false)
    private String description; // Detailed description of the compliance requirement

    @Column(nullable = false)
    private String status; // Status (e.g., Pending, Completed, Overdue)

    @Column(nullable = false)
    @JsonProperty("isComplied")
    private boolean isComplied; // Indicates if the compliance task is complied with

    @Column(nullable = false)
    private LocalDate dueDate; // Deadline for compliance completion

    @Column
    private LocalDate completionDate; // Actual date when compliance was completed

    @Column
    private String assignedTo; // Person or team responsible for the compliance task

    @Column
    private String notes; // Optional notes about the compliance task

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Severity severity; // Severity level of the compliance requirement

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Priority priority; // Priority level of the compliance task

    @Column(nullable = false)
    private LocalDateTime createdAt; // When the compliance task was created

    @Column(nullable = false)
    private String createdBy; // User who created the compliance record

    @Column
    private LocalDateTime lastModifiedAt; // When the compliance task was last updated

    @Column
    private String lastModifiedBy; // User who last modified the compliance record

    @Builder.Default
    @OneToMany(mappedBy = "complianceRecord", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ComplianceDocument> documents = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        isComplied = false; // Set default value for isComplied
        lastModifiedAt = createdAt;
        if (createdBy == null) {
            createdBy = "system"; // Default value if not set
        }
        lastModifiedBy = createdBy;
    }

    @PreUpdate
    protected void onUpdate() {
        lastModifiedAt = LocalDateTime.now();
        if (lastModifiedBy == null) {
            lastModifiedBy = "system"; // Default value if not set
        }
    }
} 