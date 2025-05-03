package com.vitira.itreasury.controller;

import com.vitira.itreasury.dto.DailyCashFlowDto;
import com.vitira.itreasury.dto.PaymentRecord;
import com.vitira.itreasury.dto.WeeklyCashFlowResponse;
import com.vitira.itreasury.enums.CategoryType;
import com.vitira.itreasury.service.DailyCashFlowService;
import com.vitira.itreasury.service.ExcelImportService;
import com.vitira.itreasury.service.ExcelProcessingService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value = "/cashflows", produces = MediaType.APPLICATION_JSON_VALUE)
public class DailyCashFlowController {

    private final DailyCashFlowService service;

    private final ExcelImportService excelImportService;

    private final ExcelProcessingService excelProcessingService;

    public DailyCashFlowController(DailyCashFlowService service, ExcelImportService excelImportService, ExcelProcessingService excelProcessingService) {
        this.service = service;
        this.excelImportService = excelImportService;
        this.excelProcessingService = excelProcessingService;
    }

    @GetMapping("/today")
    public ResponseEntity<DailyCashFlowDto> getToday() {
        DailyCashFlowDto dto = service.getTodaysCashFlow();
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(dto);
    }

    @GetMapping("/weekly")
    public ResponseEntity<WeeklyCashFlowResponse> getWeekly() {
        List<DailyCashFlowDto> week = service.getNext7DaysCashFlow();
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(new WeeklyCashFlowResponse(week));
    }

    @PostMapping(value = "/upload/{type}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> uploadCashFlows(
            @PathVariable("type") CategoryType type,
            @RequestParam("file") MultipartFile file) {
        excelImportService.importFile(file, type);
        return ResponseEntity.ok(Map.of("status","import started"));
    }

    @PostMapping(value = "/import",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PaymentRecord>> importExcel(@RequestParam("file") MultipartFile file) throws IOException {
        List<PaymentRecord> processed = excelProcessingService.process(file);
        return ResponseEntity.ok(processed);
    }
}