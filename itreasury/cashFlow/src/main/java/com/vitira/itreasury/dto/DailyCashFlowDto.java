package com.vitira.itreasury.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Data
public class DailyCashFlowDto {
    private LocalDate date;
    private Map<String, List<CategoryAmountDto>> inflows;
    private Map<String, List<CategoryAmountDto>> outflows;

    public DailyCashFlowDto(LocalDate date,
                            Map<String, List<CategoryAmountDto>> inflows,
                            Map<String, List<CategoryAmountDto>> outflows) {
        this.date = date;
        this.inflows = inflows;
        this.outflows = outflows;
    }
}