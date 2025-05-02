package com.vitira.itreasury.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vitira.itreasury.entity.ComplianceDocument;
import com.vitira.itreasury.enums.ComplianceFrequency;
import com.vitira.itreasury.enums.Priority;
import com.vitira.itreasury.enums.Severity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ComplianceRecordDTO {
    private Long id;
    private String name;
    private String complianceCategory;
    private ComplianceFrequency complianceFrequency;
    private String description;
    private String status;
    @JsonProperty("isComplied")
    private boolean isComplied;
    private LocalDate dueDate;
    private LocalDate completionDate;
    private String assignedTo;
    private String notes;
    private Severity severity;
    private Priority priority;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime lastModifiedAt;
    private String lastModifiedBy;

    @Builder.Default
    private List<ComplianceDocument> documents = new ArrayList<>();
} 