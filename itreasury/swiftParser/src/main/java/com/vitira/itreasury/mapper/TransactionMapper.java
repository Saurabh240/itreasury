package com.vitira.itreasury.mapper;

import com.vitira.itreasury.dto.TransactionDTO;
import com.vitira.itreasury.dto.TransactionRequest;
import com.vitira.itreasury.entity.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionMapper {

    public Transaction toTransaction(TransactionRequest request) {
        return Transaction.builder()
                .debitCreditMark(request.getDebitCreditMark())
                .amount(request.getAmount())
                .transactionType(request.getTransactionType())
                .supplementaryInfo(request.getSupplementaryInfo())
                .description(request.getDescription())
                .build();
    }

    public TransactionDTO toTransactionDTO(Transaction transaction) {
        return TransactionDTO.builder()
                .categoryName(transaction.getCategory().getCategoryName())
                .amount(transaction.getAmount())
                .currency(transaction.getCurrency())
                .categoryDescription(transaction.getCategory().getCategoryDescription())
                .build();
    }
}
