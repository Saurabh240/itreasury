/*
package com.vitira.itreasury.service.impl;

import com.vitira.itreasury.dto.CategoryAmountDto;
import com.vitira.itreasury.dto.DailyCashFlowDto;
import com.vitira.itreasury.entity.DailyCashFlowEntity;
import com.vitira.itreasury.enums.CategoryType;
import com.vitira.itreasury.repository.DailyCashFlowRepository;
import com.vitira.itreasury.service.DailyCashFlowService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DailyCashFlowServiceImpl implements DailyCashFlowService {

    private final DailyCashFlowRepository repo;

    public DailyCashFlowServiceImpl(DailyCashFlowRepository repo) {
        this.repo = repo;
    }

    @Override
    public DailyCashFlowDto getTodaysCashFlow() {
        LocalDate today = LocalDate.now();

        List<DailyCashFlowEntity> erpList = repo.findByDate(today);
//        List<CategoryAmountDto> manualIns  = getManualInflowsFor(today);
//        List<CategoryAmountDto> manualOuts = getManualOutflowsFor(today);

//        return buildDailyDto(today, erpList, manualIns, manualOuts);
        return null;
    }

    @Override
    public List<DailyCashFlowDto> getNext7DaysCashFlow() {
        List<DailyCashFlowDto> week = new ArrayList<>();
        for (int i = 1; i <= 7; i++) {
            LocalDate d = LocalDate.now().plusDays(i);

            List<DailyCashFlowEntity> erpEntities  = repo.findByDate(d);

//            week.add(buildDailyDto(d, erpEntities, manualIns, manualOuts));
        }
        return week;
    }

    private DailyCashFlowDto buildDailyDto(LocalDate date,
                                           List<DailyCashFlowEntity> erpEntities,
                                           List<CategoryAmountDto> manualInflows,
                                           List<CategoryAmountDto> manualOutflows) {
        // 1) group ERP rows by type and category
        Map<CategoryType, Map<String, BigDecimal>> grouped = erpEntities.stream()
                .collect(Collectors.groupingBy(
                        DailyCashFlowEntity::getType,
                        Collectors.groupingBy(
                                DailyCashFlowEntity::getCategory,
                                Collectors.reducing(BigDecimal.ZERO, DailyCashFlowEntity::getAmount, BigDecimal::add)
                        )
                ));

        List<CategoryAmountDto> erpInflows  = toDtoList(grouped.get(CategoryType.INFLOW));
        List<CategoryAmountDto> erpOutflows = toDtoList(grouped.get(CategoryType.OUTFLOW));

        // 2) assemble the two‐source maps
        Map<String, List<CategoryAmountDto>> inflows = new LinkedHashMap<>();
        inflows.put("ERP",     erpInflows);
        inflows.put("Manual",  manualInflows);

        Map<String, List<CategoryAmountDto>> outflows = new LinkedHashMap<>();
        outflows.put("ERP",     erpOutflows);
        outflows.put("Manual",  manualOutflows);

        return new DailyCashFlowDto(date, inflows, outflows);
    }

    private List<CategoryAmountDto> toDtoList(Map<String, BigDecimal> map) {
        if (map == null) return Collections.emptyList();
        return map.entrySet().stream()
                .map(e -> new CategoryAmountDto(e.getKey(), e.getValue()))
                .collect(Collectors.toList());
    }
}*/
