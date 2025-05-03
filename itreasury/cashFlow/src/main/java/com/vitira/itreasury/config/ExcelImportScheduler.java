package com.vitira.itreasury.config;

import com.vitira.itreasury.dto.CategoryType;
import com.vitira.itreasury.service.ExcelImportService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;

@Component
public class ExcelImportScheduler {

    private final ExcelImportService importService;

    @Value("${cashflow.import.inflow-dir:/data/inflows}")
    private String inflowDir;

    @Value("${cashflow.import.outflow-dir:/data/outflows}")
    private String outflowDir;

    public ExcelImportScheduler(ExcelImportService importService) {
        this.importService = importService;
    }

    @Scheduled(cron = "0 0 18 * * *", zone = "Asia/Kolkata")
    public void runImportTwiceDaily() {
        // process all .xlsx files found in each folder
        processFolder(inflowDir, CategoryType.INFLOW);
        processFolder(outflowDir, CategoryType.OUTFLOW);    }

    private void processFolder(String folderPath, CategoryType type) {
        File dir = new File(folderPath);
        if (!dir.isDirectory()) return;

        File[] excelFiles = dir.listFiles((d, name) -> name.toLowerCase().endsWith(".xlsx"));
        if (excelFiles == null) return;

        for (File file : excelFiles) {
            try (FileInputStream fis = new FileInputStream(file)) {
                MultipartFile multipart = new MockMultipartFile(
                        "file",                             // form field name (unused here)
                        file.getName(),                     // original filename
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                        fis
                );

                importService.importFile(multipart, type);

                // optional: move or delete the file after successful import
                // Files.move(file.toPath(), Paths.get(folderPath, "processed", file.getName()));
            } catch (Exception ex) {
                // log and continue with next file
                System.err.printf("Failed to import %s: %s%n", file.getName(), ex.getMessage());
            }
        }
    }
}
