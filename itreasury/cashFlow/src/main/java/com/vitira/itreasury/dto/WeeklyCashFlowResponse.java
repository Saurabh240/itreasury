package com.vitira.itreasury.dto;

import lombok.Data;

import java.util.List;

@Data
public class WeeklyCashFlowResponse {
    private List<DailyCashFlowDto> next7Days;

    public WeeklyCashFlowResponse() {}
    public WeeklyCashFlowResponse(List<DailyCashFlowDto> next7Days) {
        this.next7Days = next7Days;
    }
}