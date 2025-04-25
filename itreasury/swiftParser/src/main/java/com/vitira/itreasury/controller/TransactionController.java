package com.vitira.itreasury.controller;

import com.vitira.itreasury.dto.TransactionDTO;
import com.vitira.itreasury.dto.TransactionRequest;
import com.vitira.itreasury.entity.Transaction;
import com.vitira.itreasury.mapper.TransactionMapper;
import com.vitira.itreasury.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private TransactionMapper transactionMapper;

    @PostMapping
    public void saveTransaction(@Valid @RequestBody TransactionRequest request) {
        Transaction transaction = transactionMapper.toTransaction(request);
        transactionService.saveTransaction(transaction);
    }

    @GetMapping
    public List<TransactionDTO> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    @GetMapping("/{id}")
    public TransactionDTO getTransactionById(@PathVariable("id") Long id) {
        return transactionService.getTransactionById(id);
    }

    @PostMapping("/{id}")
    public void updateTransaction(@PathVariable("id") Long id, TransactionRequest request) {
        Transaction transaction = transactionMapper.toTransaction(request);
        transaction.setId(id);
        transactionService.updateTransaction(transaction);
    }

    @PostMapping("/delete/{id}")
    public void deleteTransaction(@PathVariable("id") Long id) {
        transactionService.deleteTransaction(id);
    }

}