package com.vitira.itreasury.service;

import com.vitira.itreasury.dto.TransactionDTO;
import com.vitira.itreasury.entity.Transaction;
import com.vitira.itreasury.mapper.TransactionMapper;
import com.vitira.itreasury.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionMapper mapper;

    public void saveTransaction(Transaction transaction) {
        try {
            transactionRepository.save(transaction);
        } catch (DataIntegrityViolationException e) {
            System.err.println("Error saving transaction: " + e.getMessage());
        }
    }

    public TransactionDTO getTransactionById(Long id) {

        Transaction transaction = transactionRepository.findById(id).orElse(null);
        return transaction != null ? mapper.toTransactionDTO(transaction) : null;
    }

    public List<TransactionDTO> getAllTransactions() {
        List<Transaction> transactions = transactionRepository.findAll();
        return transactions.stream()
                .map(mapper::toTransactionDTO)
                .toList();
    }

    public void deleteTransaction(Long id) {
        try {
            transactionRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            System.err.println("Error deleting transaction: " + e.getMessage());
        }
    }

    public void updateTransaction(Transaction transaction) {
        try {
            transactionRepository.save(transaction);
        } catch (DataIntegrityViolationException e) {
            System.err.println("Error updating transaction: " + e.getMessage());
        }
    }

}
