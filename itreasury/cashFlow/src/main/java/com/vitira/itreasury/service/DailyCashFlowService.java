package com.vitira.itreasury.service;


import com.vitira.itreasury.dto.DailyCashFlowDto;

import java.util.List;

public interface DailyCashFlowService {
    DailyCashFlowDto getTodaysCashFlow();
    List<DailyCashFlowDto> getNext7DaysCashFlow();
}