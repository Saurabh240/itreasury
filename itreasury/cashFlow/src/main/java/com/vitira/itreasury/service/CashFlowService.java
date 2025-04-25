package com.vitira.itreasury.service;

import com.vitira.itreasury.dto.CashFlowResponse;
import com.vitira.itreasury.dto.TransactionDTO;
import com.vitira.itreasury.mapper.TransactionMapper;
import com.vitira.itreasury.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.vitira.itreasury.enums.CategoryType.INFLOW;
import static com.vitira.itreasury.enums.CategoryType.OUTFLOW;

@Service
public class CashFlowService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionMapper mapper;

    public CashFlowResponse getCashFlowData(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        Map<String, List<TransactionDTO>> cashFlowData = new HashMap<>();

        // Fetch transactions within the date range
        List<TransactionDTO> inflowResults = transactionRepository.findByCategoryTypeAndTransactionDateBetween(INFLOW, startDateTime, endDateTime);
        List<TransactionDTO> outflowResults = transactionRepository.findByCategoryTypeAndTransactionDateBetween(OUTFLOW, startDateTime, endDateTime);

        cashFlowData.put(String.valueOf(INFLOW), inflowResults);
        cashFlowData.put(String.valueOf(OUTFLOW), outflowResults);

        // Calculate totals
        BigDecimal totalInflows = calculateTotal(inflowResults);
        BigDecimal totalOutflows = calculateTotal(outflowResults);
        BigDecimal netCashFlow = totalInflows.subtract(totalOutflows);

        return CashFlowResponse.builder()
                .startDateTime(startDateTime)
                .endDateTime(endDateTime)
                .cashFlowData(cashFlowData)
                .totalInflows(totalInflows)
                .totalOutflows(totalOutflows)
                .netCashFlow(netCashFlow)
                .build();
    }

    private BigDecimal calculateTotal(List<TransactionDTO> transactions) {
        return transactions.stream()
                .map(TransactionDTO::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}