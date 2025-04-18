package com.vitira.itreasury.service;

import com.vitira.itreasury.dto.TransactionDTO;
import com.vitira.itreasury.mapper.TransactionMapper;
import com.vitira.itreasury.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.vitira.itreasury.enums.CategoryType.INFLOW;
import static com.vitira.itreasury.enums.CategoryType.OUTFLOW;

@Service
public class CashFlowService {

    @Autowired
    private TransactionRepository transactionRepository;

    private TransactionMapper mapper = new TransactionMapper();

    public Map<String, List<TransactionDTO>> getCashFlowData() {
        Map<String, List<TransactionDTO>> cashFlowData = new HashMap<>();

        List<TransactionDTO> inflowResults = transactionRepository.findTransactionsByCategoryType(INFLOW);
        List<TransactionDTO> outflowResults = transactionRepository.findTransactionsByCategoryType(OUTFLOW);

        cashFlowData.put(String.valueOf(INFLOW), inflowResults);
        cashFlowData.put(String.valueOf(OUTFLOW), outflowResults);

        return cashFlowData;
    }

}