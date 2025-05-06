package com.vitira.itreasury.dto;

import com.vitira.itreasury.enums.PaymentType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class PaymentRecord {
    private String supplierCode;
    private String docCurr;
    private String text;
    private LocalDate invoiceDate;
    private BigDecimal amount;
    private PaymentType category;
    private LocalDate       dueDate;
}