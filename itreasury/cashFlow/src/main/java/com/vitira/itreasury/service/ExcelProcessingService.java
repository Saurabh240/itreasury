package com.vitira.itreasury.service;

import com.vitira.itreasury.entity.CashFlowEntry;
import com.vitira.itreasury.enums.Source;
import com.vitira.itreasury.repository.CashFlowEntryRepository;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExcelProcessingService {
    private final CashFlowEntryRepository repo;
    private final RuleEngineService ruleEngine;

    public ExcelProcessingService(CashFlowEntryRepository repo,
                                  RuleEngineService ruleEngine) {
        this.repo        = repo;
        this.ruleEngine  = ruleEngine;
    }

    public List<CashFlowEntry> importErpSheet(MultipartFile file) throws IOException {
        List<CashFlowEntry> entries = new ArrayList<>();

        try (Workbook wb = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = wb.getSheetAt(1);

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue;
                entries.add(mapRowToEntry(row));
            }
        }
        List<CashFlowEntry> valid = ruleEngine.applyRules(entries);
        return repo.saveAll(valid);
    }

    private CashFlowEntry mapRowToEntry(Row row) {
        String docValue = cellAsString(row.getCell(13));
        String text = cellAsString(row.getCell(14));
        String supplierCode = cellAsString(row.getCell(1));
        LocalDate invoiceDate = row.getCell(9)
                .getLocalDateTimeCellValue()
                .toLocalDate();
        LocalDate dueDate = row.getCell(10)
                .getLocalDateTimeCellValue()
                .toLocalDate();
        BigDecimal amount = new BigDecimal(row.getCell(12).toString());

        return CashFlowEntry.builder()
                .invoiceDate(invoiceDate)
                .source(Source.ERP)
                .docCurrValue(docValue)
                .supplierCode(supplierCode)
                .text(text)
                .dueDate(dueDate)
                .amount(amount)
                .build();
    }

    private String cellAsString(Cell c) {
        if (c == null) return null;
        return switch (c.getCellType()) {
            case STRING  -> c.getStringCellValue();
            case NUMERIC -> NumberToTextConverter.toText(c.getNumericCellValue());
            default      -> c.toString();
        };
    }
}
