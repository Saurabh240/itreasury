package com.vitira.itreasury.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CategoryAmountDto {
    private String category;
    private BigDecimal amount;

    public CategoryAmountDto() {}
    public CategoryAmountDto(String category, BigDecimal amount) {
        this.category = category;
        this.amount   = amount;
    }
}