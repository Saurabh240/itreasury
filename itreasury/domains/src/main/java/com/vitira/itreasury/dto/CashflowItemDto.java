package com.vitira.itreasury.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class CashflowItemDto {
    private String category;
    private BigDecimal amount;
}
