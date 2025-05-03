package com.vitira.itreasury.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class PaymentRecord {
    private String supplierCode;
    private String docCurr;
    private String text;
    private LocalDate invoiceDate;    // from Column K
    private BigDecimal amount;         // from Column M
    private PaymentCategory category;
    private LocalDate       dueDate;
}