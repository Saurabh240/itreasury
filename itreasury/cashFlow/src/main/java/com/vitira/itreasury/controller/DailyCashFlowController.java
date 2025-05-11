package com.vitira.itreasury.controller;

import com.vitira.itreasury.dto.DailyCashFlowDto;
import com.vitira.itreasury.dto.WeeklyCashFlowResponse;
import com.vitira.itreasury.service.DailyCashFlowService;
import com.vitira.itreasury.service.ExcelProcessingService;
import com.vitira.itreasury.service.ManualCashFlowImportService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


@RestController
@RequestMapping(value = "/cashflows", produces = MediaType.APPLICATION_JSON_VALUE)
public class DailyCashFlowController {

    private final DailyCashFlowService service;

    private final ManualCashFlowImportService manualCashFlowImportService;

    private final ExcelProcessingService excelProcessingService;

    public DailyCashFlowController(DailyCashFlowService service, ManualCashFlowImportService manualCashFlowImportService, ExcelProcessingService excelProcessingService) {
        this.service = service;
        this.manualCashFlowImportService = manualCashFlowImportService;
        this.excelProcessingService = excelProcessingService;
    }

    @GetMapping("/today")
    public ResponseEntity<DailyCashFlowDto> getDaily(
            @RequestParam(name = "date", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate date
    ) {
        LocalDate target = date != null
                ? date
                : LocalDate.now(ZoneId.of("Asia/Kolkata"));

        DailyCashFlowDto dto = service.getDailyCashFlow(target);
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(dto);
    }

    @GetMapping("/weekly")
    public ResponseEntity<WeeklyCashFlowResponse> getWeekly(
            @RequestParam(name = "startDate", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate start,

            @RequestParam(name = "endDate",   required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate end
    ) {
        LocalDate today = LocalDate.now(ZoneId.of("Asia/Kolkata"));
        LocalDate startDate = (start != null) ? start : today.plusDays(1);
        LocalDate endDate   = (end   != null) ? end   : today.plusDays(7);

        List<DailyCashFlowDto> weekDtos = service.getCashFlowBetween(startDate, endDate);
        WeeklyCashFlowResponse resp = new WeeklyCashFlowResponse(weekDtos);

        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(resp);
    }

    @PostMapping(value = "/import/manual",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> importExcel(@RequestParam("file") MultipartFile file) throws IOException {
        manualCashFlowImportService.importFromExcel(file);
        return ResponseEntity.ok("Manual cashflows imported");
    }

    @PostMapping(value = "/import/erp",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> importERPExcel(@RequestParam("file") MultipartFile file) throws IOException {
        excelProcessingService.importErpSheet(file);
        return ResponseEntity.ok("ERP cashflows imported");
    }
}