package com.vitira.itreasury.controller;

import com.vitira.itreasury.dto.TransactionRequest;
import com.vitira.itreasury.entity.Transaction;
import com.vitira.itreasury.mapper.TransactionMapper;
import com.vitira.itreasury.service.TransactionCategorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions/categorize")
public class TransactionCategorizationController {

    @Autowired
    private TransactionCategorizationService transactionCategorizationService;

    @Autowired
    private TransactionMapper transactionMapper;

    @PostMapping
    public Transaction categorizeTransaction(@RequestBody TransactionRequest request) {
        return transactionCategorizationService.categorizeTransaction(transactionMapper.toTransaction(request));
    }

    @PostMapping("/bulk")
    public List<Transaction> categorizeBulkTransactions(@RequestBody List<TransactionRequest> requests) {
        return requests.stream()
                .map(transactionMapper::toTransaction)
                .map(transactionCategorizationService::categorizeTransaction)
                .toList();
    }
}