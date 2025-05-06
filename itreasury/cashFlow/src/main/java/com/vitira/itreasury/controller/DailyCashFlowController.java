package com.vitira.itreasury.controller;

import com.vitira.itreasury.dto.DailyCashFlowDto;
import com.vitira.itreasury.dto.WeeklyCashFlowResponse;
import com.vitira.itreasury.service.DailyCashFlowService;
import com.vitira.itreasury.service.ExcelProcessingService;
import com.vitira.itreasury.service.ManualCashFlowImportService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

/*    @PostMapping(value = "/upload/{type}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> uploadCashFlows(
            @PathVariable("type") CategoryType type,
            @RequestParam("file") MultipartFile file) {
        excelImportService.importFile(file, type);
        return ResponseEntity.ok(Map.of("status","import started"));
    }*/

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

//    @PostMapping("/load")
//    public ResponseEntity<String> loadManualData(
//            @RequestParam("date")
//            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
//            LocalDate date) {
//        manualCashFlowService.getManualInflowsFor(date);
//        manualCashFlowService.getManualOutflowsFor(date);
//        return ResponseEntity.ok("Manual cash flows imported for " + date);
//    }
}