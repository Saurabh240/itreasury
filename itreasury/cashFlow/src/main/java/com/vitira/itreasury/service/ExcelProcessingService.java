package com.vitira.itreasury.service;

import com.vitira.itreasury.dto.PaymentRecord;
import com.vitira.itreasury.exception.ExcelProcessingException;
import com.vitira.itreasury.rules.RuleEngine;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExcelProcessingService {
    private final RuleEngine ruleEngine;

    public ExcelProcessingService(RuleEngine ruleEngine) {
        this.ruleEngine = ruleEngine;
    }

    public List<PaymentRecord> process(MultipartFile file) throws IOException {
        try (Workbook wb = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = wb.getSheetAt(1);
            List<PaymentRecord> output = new ArrayList<>();

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue;
                PaymentRecord rec = mapRow(row);
                ruleEngine.classify(rec);
                output.add(rec);
            }
            return output;
        }
    }

    private PaymentRecord mapRow(Row row) {
        PaymentRecord r = new PaymentRecord();
        r.setSupplierCode(cellAsString(row.getCell(1))); // adjust indices!
        Cell dateCell = row.getCell(10);
        if (!DateUtil.isCellDateFormatted(dateCell)) {
            throw new ExcelProcessingException(
                    "Missing or invalid invoice date in Column K at row " + (row.getRowNum()+1)
            );
        }
        r.setInvoiceDate(
                dateCell.getLocalDateTimeCellValue().toLocalDate()
        );

        Cell amtCell = row.getCell(12);
        if (amtCell == null) {
            throw new ExcelProcessingException(
                    "Missing amount in Column M at row " + (row.getRowNum()+1)
            );
        }
        r.setAmount(new BigDecimal(row.getCell(12).toString()));
        r.setDocCurr(cellAsString(row.getCell(13)));
        r.setText(cellAsString(row.getCell(14)));
        return r;
    }

    private String cellAsString(Cell c) {
        if (c == null) return null;
        return switch (c.getCellType()) {
            case STRING -> c.getStringCellValue();
            case NUMERIC -> NumberToTextConverter.toText(c.getNumericCellValue());
            default      -> c.toString();
        };
    }
}
