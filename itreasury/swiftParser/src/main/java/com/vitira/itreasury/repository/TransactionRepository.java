package com.vitira.itreasury.repository;

import com.vitira.itreasury.dto.TransactionDTO;
import com.vitira.itreasury.entity.Transaction;
import com.vitira.itreasury.enums.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("SELECT new com.vitira.itreasury.dto.TransactionDTO(tc.categoryName, SUM(t.amount), 'INR', tc.categoryDescription) " +
            "FROM Transaction t JOIN t.category tc " +
            "WHERE tc.categoryType = :categoryType GROUP BY tc.categoryName, tc.categoryDescription")
    List<TransactionDTO> findTransactionsByCategoryType(@Param("categoryType") CategoryType categoryType);
}