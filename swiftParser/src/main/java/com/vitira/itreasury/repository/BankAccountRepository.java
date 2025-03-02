package com.vitira.itreasury.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vitira.itreasury.entity.BankAccount;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
}
