package com.vitira.itreasury.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class WeeklyCashFlowResponse {
    private List<DailyCashFlowDto> next7Days;
}
