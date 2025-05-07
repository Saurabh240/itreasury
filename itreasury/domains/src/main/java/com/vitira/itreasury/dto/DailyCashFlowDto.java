package com.vitira.itreasury.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class DailyCashFlowDto {

    private List<Integer> date;

    private Map<String, List<CashflowItemDto>> inflows;
    private Map<String, List<CashflowItemDto>> outflows;
}
