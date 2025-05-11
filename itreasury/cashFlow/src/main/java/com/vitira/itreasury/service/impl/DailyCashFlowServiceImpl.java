package com.vitira.itreasury.service.impl;

import com.vitira.itreasury.dto.CashflowItemDto;
import com.vitira.itreasury.dto.DailyCashFlowDto;
import com.vitira.itreasury.entity.CashFlowEntry;
import com.vitira.itreasury.enums.CategoryType;
import com.vitira.itreasury.enums.Source;
import com.vitira.itreasury.repository.CashFlowEntryRepository;
import com.vitira.itreasury.service.DailyCashFlowService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DailyCashFlowServiceImpl implements DailyCashFlowService {

    private final CashFlowEntryRepository repo;

    public DailyCashFlowServiceImpl(CashFlowEntryRepository repo) {
        this.repo = repo;
    }

    @Override
    public DailyCashFlowDto getDailyCashFlow(LocalDate date) {
        List<CashFlowEntry> entries = repo.findByInvoiceDate(date);
        return buildDailyDto(date, entries);
    }

    @Override
    public List<DailyCashFlowDto> getCashFlowBetween(LocalDate start, LocalDate end) {
        List<CashFlowEntry> entries = repo.findByInvoiceDateBetween(start, end);

        // bucket per day
        Map<LocalDate, List<CashFlowEntry>> buckets = new TreeMap<>();
        for (LocalDate d = start; !d.isAfter(end); d = d.plusDays(1)) {
            buckets.put(d, new ArrayList<>());
        }
        entries.forEach(e ->
                buckets.computeIfAbsent(e.getInvoiceDate(), dd -> new ArrayList<>())
                        .add(e)
        );

        // map to DTOs
        return buckets.entrySet().stream()
                .map(e -> buildDailyDto(e.getKey(), e.getValue()))
                .collect(Collectors.toList());
    }

    private DailyCashFlowDto buildDailyDto(LocalDate date, List<CashFlowEntry> entries) {
        Map<String, Map<String, BigDecimal>> inflowSums = new LinkedHashMap<>();
        Map<String, Map<String, BigDecimal>> outflowSums = new LinkedHashMap<>();
        for (String src : List.of("ERP", "Manual")) {
            inflowSums.put(src, new LinkedHashMap<>());
            outflowSums.put(src, new LinkedHashMap<>());
        }

        for (CashFlowEntry e : entries) {
            String srcKey    = e.getSource() == Source.MANUAL ? "Manual" : "ERP";
            String category  = e.getPaymentType().getValues();
            BigDecimal amount = e.getAmount();

            Map<String, BigDecimal> bucket =
                    e.getCategoryType() == CategoryType.INFLOW
                            ? inflowSums.get(srcKey)
                            : outflowSums.get(srcKey);

            bucket.merge(category, amount, BigDecimal::add);
        }

        Map<String, List<CashflowItemDto>> inflows = new LinkedHashMap<>();
        Map<String, List<CashflowItemDto>> outflows = new LinkedHashMap<>();
        for (String src : List.of("ERP", "Manual")) {
            List<CashflowItemDto> inflowItems = inflowSums.get(src).entrySet().stream()
                    .map(ent -> new CashflowItemDto(ent.getKey(), ent.getValue()))
                    .collect(Collectors.toList());
            List<CashflowItemDto> outflowItems = outflowSums.get(src).entrySet().stream()
                    .map(ent -> new CashflowItemDto(ent.getKey(), ent.getValue()))
                    .collect(Collectors.toList());

            inflows .put(src, inflowItems);
            outflows.put(src, outflowItems);
        }

        List<Integer> dateArr = List.of(
                date.getYear(),
                date.getMonthValue(),
                date.getDayOfMonth()
        );

        return DailyCashFlowDto.builder()
                .date(dateArr)
                .inflows(inflows)
                .outflows(outflows)
                .build();
    }
}