package com.vitira.itreasury.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vitira.itreasury.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
