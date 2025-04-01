package com.vitira.itreasury.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vitira.itreasury.entity.BankAccount;

import java.util.List;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {

	// Custom query method to find a BankAccount by accountNumber
	BankAccount findByAccountNumber(String accountNumber);

	// Custom query method to find a BankAccounts by companyId
	List<BankAccount> findByCompanyId(Long companyId);

}
