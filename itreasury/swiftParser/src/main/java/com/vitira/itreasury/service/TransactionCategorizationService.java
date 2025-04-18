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

    @Transactional
    public Transaction categorizeTransaction(Transaction transaction) {
        // Fetch all categories
        List<TransactionCategory> categories = categoryRepository.findAll();
        CategoryType categoryType = getCategoryType(transaction.getDebitCreditMark());
        // Match transaction description with category keywords
        for (TransactionCategory category : categories) {
            if (categoryType == category.getCategoryType()) {
                // Check both descKeywords and supplementaryKeywords
                if (matchesKeywords(transaction, category)) {
                    transaction.setCategory(category);
                    return transaction; // Stop after the first match
                }
            }
        }

        // If no category matched, set a default category (if needed)
        TransactionCategory defaultCategory = categoryRepository.findByCategoryTypeAndCategoryName(categoryType, DEFAULT_CATEGORY_NAME);

        transaction.setCategory(defaultCategory);
        return transaction;
    }

    private boolean matchesKeywords(Transaction transaction, TransactionCategory category) {
        if (transaction.getDescription() == null && transaction.getSupplementaryInfo() == null) {
            return false;
        }

        String supplementaryInfo = transaction.getSupplementaryInfo() != null ? transaction.getSupplementaryInfo() : "";
        for (String keyword : category.getSupplementaryKeywords().split(",")) {
            if (supplementaryInfo.toLowerCase().contains(keyword.toLowerCase())) {
                return true;
            }
        }

        String desc = transaction.getDescription() != null ? transaction.getDescription() : "";
        for (String keyword : category.getDescKeywords().split(",")) {
            if (desc.toLowerCase().contains(keyword.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    private CategoryType getCategoryType(String debitCreditMark) {
        return "C".equalsIgnoreCase(debitCreditMark) ? INFLOW : OUTFLOW;
    }

}
