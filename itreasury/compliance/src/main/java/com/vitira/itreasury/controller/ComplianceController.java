package com.vitira.itreasury.controller;

import com.vitira.itreasury.dto.ComplianceRecordDTO;
import com.vitira.itreasury.dto.ComplianceRecordRequestDTO;
import com.vitira.itreasury.enums.Priority;
import com.vitira.itreasury.enums.Severity;
import com.vitira.itreasury.service.ComplianceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/compliance")
public class ComplianceController {

    private final ComplianceService complianceService;

    @Autowired
    public ComplianceController(ComplianceService complianceService) {
        this.complianceService = complianceService;
    }

    @PostMapping
    public ResponseEntity<ComplianceRecordDTO> createComplianceRecord(
            @RequestBody ComplianceRecordRequestDTO requestDTO,
            Authentication authentication) {
        String username = authentication.getName();
        ComplianceRecordDTO createdRecord = complianceService.createComplianceRecord(requestDTO, username);
        return ResponseEntity.ok(createdRecord);
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<ComplianceRecordDTO>> createComplianceRecords(
            @RequestBody List<ComplianceRecordRequestDTO> complianceRecords,
            Authentication authentication) {
        String username = authentication.getName();
        List<ComplianceRecordDTO> createdRecord = complianceService.createComplianceRecords(complianceRecords, username);
        return ResponseEntity.ok(createdRecord);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ComplianceRecordDTO> updateComplianceRecord(
            @PathVariable("id") Long id,
            @RequestBody ComplianceRecordRequestDTO requestDTO,
            Authentication authentication) {
        String username = authentication.getName();
        ComplianceRecordDTO updatedRecord = complianceService.updateComplianceRecord(id, requestDTO, username);
        return ResponseEntity.ok(updatedRecord);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComplianceRecordDTO> getComplianceRecord(@PathVariable("id")  Long id) {
        ComplianceRecordDTO record = complianceService.getComplianceRecord(id);
        return ResponseEntity.ok(record);
    }

    @GetMapping
    public ResponseEntity<List<ComplianceRecordDTO>> getAllComplianceRecords() {
        List<ComplianceRecordDTO> records = complianceService.getAllComplianceRecords();
        return ResponseEntity.ok(records);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComplianceRecord(@PathVariable("id")  Long id) {
        complianceService.deleteComplianceRecord(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<ComplianceRecordDTO>> getComplianceRecordsByStatus(@PathVariable("status")  String status) {
        List<ComplianceRecordDTO> records = complianceService.getComplianceRecordsByStatus(status);
        return ResponseEntity.ok(records);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<ComplianceRecordDTO>> getComplianceRecordsByCategory(@PathVariable("category") String category) {
        List<ComplianceRecordDTO> records = complianceService.getComplianceRecordsByCategory(category);
        return ResponseEntity.ok(records);
    }

    @GetMapping("/severity/{severity}")
    public ResponseEntity<List<ComplianceRecordDTO>> getComplianceRecordsBySeverity(
            @PathVariable("severity") Severity severity) {
        List<ComplianceRecordDTO> records = complianceService.getComplianceRecordsBySeverity(severity);
        return ResponseEntity.ok(records);
    }

    @GetMapping("/priority/{priority}")
    public ResponseEntity<List<ComplianceRecordDTO>> getComplianceRecordsByPriority(
            @PathVariable("priority") Priority priority) {
        List<ComplianceRecordDTO> records = complianceService.getComplianceRecordsByPriority(priority);
        return ResponseEntity.ok(records);
    }

    @GetMapping("/severity/{severity}/priority/{priority}")
    public ResponseEntity<List<ComplianceRecordDTO>> getComplianceRecordsBySeverityAndPriority(
            @PathVariable("severity") Severity severity,
            @PathVariable("priority") Priority priority) {
        List<ComplianceRecordDTO> records = complianceService.getComplianceRecordsBySeverityAndPriority(severity, priority);
        return ResponseEntity.ok(records);
    }
} 