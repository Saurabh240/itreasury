package com.vitira.itreasury.service;

import com.vitira.itreasury.enums.CategoryType;
import com.vitira.itreasury.enums.PaymentType;
import com.vitira.itreasury.enums.Urgency;
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
import java.util.stream.Collectors;

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

//        List<CashFlowEntry> valid = ruleEngine.applyRules(entries);
        List<CashFlowEntry> toSave = entries.stream()
                .map(entry -> repo.findByPaymentTypeAndUrgencyAndDueDate(
                        entry.getPaymentType(),
                        entry.getUrgency(),
                        entry.getDueDate()
                )
                .map(existing -> {
                    existing.setAmount(entry.getAmount());
                    existing.setInvoiceDate(entry.getInvoiceDate());
                    existing.setDueDate(entry.getInvoiceDate());
                    existing.setCategoryType(entry.getCategoryType());
                    return existing;
                })
                .orElse(entry)).collect(Collectors.toList());
        repo.saveAll(toSave);
    }

    private CashFlowEntry mapRowToEntry(Row row) {
        BigDecimal amount;
        CategoryType categoryType;
        PaymentType paymentType = PaymentType.fromString(row.getCell(0).getStringCellValue());
        String urgency = Urgency.fromString(row.getCell(1).getStringCellValue()).toString();
        if (row.getCell(2).getNumericCellValue() !=0) {
            amount = BigDecimal.valueOf(row.getCell(2).getNumericCellValue());
            categoryType = CategoryType.OUTFLOW;
        } else {
            amount = BigDecimal.valueOf(row.getCell(3).getNumericCellValue());
            categoryType = CategoryType.INFLOW;
        }
        LocalDate date      = row.getCell(4).getLocalDateTimeCellValue().toLocalDate();
        LocalDate dueDate   = row.getCell(4).getLocalDateTimeCellValue().toLocalDate();

        return CashFlowEntry.builder()
                .invoiceDate(date)
                .source(Source.MANUAL)
                .paymentType(paymentType)
                .urgency(Urgency.valueOf(urgency))
                .dueDate(dueDate)
                .amount(amount)
                .categoryType(categoryType)
                .build();
    }
}
