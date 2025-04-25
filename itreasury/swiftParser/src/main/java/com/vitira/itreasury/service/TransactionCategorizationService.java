package com.vitira.itreasury.service;

import com.vitira.itreasury.entity.Transaction;
import com.vitira.itreasury.entity.TransactionCategory;
import com.vitira.itreasury.enums.CategoryType;
import com.vitira.itreasury.repository.TransactionCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.vitira.itreasury.enums.CategoryType.INFLOW;
import static com.vitira.itreasury.enums.CategoryType.OUTFLOW;

@Service
public class TransactionCategorizationService {

    @Autowired
    private TransactionCategoryRepository categoryRepository;

    private final String DEFAULT_CATEGORY_NAME = "Miscellaneous";
    private final String FOREIGN_TRANSACTION_CODE = "F";
    private final String NORMAL_TRANSACTION_CODE = "N";

    @Transactional
    public Transaction categorizeTransaction(Transaction transaction) {
        // Fetch all categories
        List<TransactionCategory> categories = categoryRepository.findAll();
        CategoryType categoryType = getCategoryType(transaction.getDebitCreditMark());
        
        // Match transaction based on hierarchical criteria
        for (TransactionCategory category : categories) {
            if (categoryType == category.getCategoryType()) {
                if (matchesHierarchicalCriteria(transaction, category)) {
                    transaction.setCategory(category);
                    return transaction;
                }
            }
        }

        // If no category matched, set a default category
        TransactionCategory defaultCategory = categoryRepository.findByCategoryTypeAndCategoryName(categoryType, DEFAULT_CATEGORY_NAME);
        transaction.setCategory(defaultCategory);
        return transaction;
    }

    private boolean matchesHierarchicalCriteria(Transaction transaction, TransactionCategory category) {
        // Step 1: Check identification code match
        String identificationCode = transaction.getIdentificationCode() != null ? transaction.getIdentificationCode() : "";
        boolean identificationCodeMatch = false;
        
        for (String code : category.getIdentificationCodes().split(",")) {
            if (identificationCode.equalsIgnoreCase(code.trim())) {
                identificationCodeMatch = true;
                break;
            }
        }
        
        if (!identificationCodeMatch) {
            return false;
        }
        
        // Step 2: Check transaction type code if identification code matched
        String transactionTypeCode = transaction.getTransactionTypeCode() != null ? transaction.getTransactionTypeCode() : "";
        boolean transactionTypeMatch = false;
        
        for (String code : category.getTransactionTypeCodes().split(",")) {
            String trimmedCode = code.trim();
            if (trimmedCode.equalsIgnoreCase(FOREIGN_TRANSACTION_CODE) && 
                transactionTypeCode.equalsIgnoreCase(FOREIGN_TRANSACTION_CODE)) {
                transactionTypeMatch = true;
                break;
            } else if (trimmedCode.equalsIgnoreCase(NORMAL_TRANSACTION_CODE) && 
                      transactionTypeCode.equalsIgnoreCase(NORMAL_TRANSACTION_CODE)) {
                transactionTypeMatch = true;
                break;
            }
        }
        
        if (!transactionTypeMatch) {
            return false;
        }
        
        // Step 3: Check description keywords if both previous steps matched
        String description = transaction.getDescription() != null ? transaction.getDescription() : "";
        for (String keyword : category.getDescKeywords().split(",")) {
            if (description.toLowerCase().contains(keyword.toLowerCase().trim())) {
                return true;
            }
        }
        
        return false;
    }

    private CategoryType getCategoryType(String debitCreditMark) {
        return "C".equalsIgnoreCase(debitCreditMark) ? INFLOW : OUTFLOW;
    }
}

