package com.vitira.itreasury.service;

import com.vitira.itreasury.enums.Source;
import com.vitira.itreasury.entity.CashFlowEntry;
import com.vitira.itreasury.repository.CashFlowEntryRepository;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ManualCashFlowImportService {

    private final CashFlowEntryRepository repo;
    private final RuleEngineService ruleEngine;

    public ManualCashFlowImportService(CashFlowEntryRepository repo,
                                       RuleEngineService ruleEngine) {
        this.repo = repo;
        this.ruleEngine = ruleEngine;
    }

    public void importFromExcel(MultipartFile file) throws IOException {
        List<CashFlowEntry> entries = new ArrayList<>();

        try (Workbook wb = WorkbookFactory.create(file.getInputStream())) {
            Sheet sheet = wb.getSheetAt(0);

            for (int i = 5; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;
                CashFlowEntry entry = mapRowToEntry(row);
                entries.add(entry);
            }
        }

        List<CashFlowEntry> valid = ruleEngine.applyRules(entries);

        repo.saveAll(valid);
    }

    private CashFlowEntry mapRowToEntry(Row row) {
        LocalDate date      = row.getCell(4).getLocalDateTimeCellValue().toLocalDate();
        String category     = row.getCell(0).getStringCellValue();
        LocalDate dueDate   = row.getCell(4).getLocalDateTimeCellValue().toLocalDate();
        BigDecimal amount   = BigDecimal.valueOf(row.getCell(2).getNumericCellValue());

        return CashFlowEntry.builder()
                .invoiceDate(date)
                .source(Source.MANUAL)
                .category(category)
                .dueDate(dueDate)
                .amount(amount)
                .build();
    }
}
