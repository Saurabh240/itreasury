package com.vitira.itreasury.repository;

import com.vitira.itreasury.entity.TransactionCategory;
import com.vitira.itreasury.enums.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionCategoryRepository extends JpaRepository<TransactionCategory, Long> {
    TransactionCategory findByCategoryTypeAndCategoryName(CategoryType categoryType, String categoryName);
}