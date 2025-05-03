package com.vitira.itreasury.dto;

import com.vitira.itreasury.enums.CategoryType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class CreateDailyCashflowRequest {
    @NotNull
    private LocalDate date;

    @NotBlank
    private String category;

    @NotNull
    private BigDecimal amount;

    @NotNull
    private CategoryType type;

    private String description;
}