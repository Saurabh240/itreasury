package com.vitira.itreasury.service;


import com.vitira.itreasury.dto.DailyCashFlowDto;

import java.time.LocalDate;
import java.util.List;

public interface DailyCashFlowService {
    DailyCashFlowDto getDailyCashFlow(LocalDate date);
    List<DailyCashFlowDto> getCashFlowBetween(LocalDate startDate, LocalDate endDate);
}