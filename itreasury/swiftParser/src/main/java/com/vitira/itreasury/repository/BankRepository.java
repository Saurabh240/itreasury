package com.vitira.itreasury.repository;

import com.vitira.itreasury.entity.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankRepository extends JpaRepository<Bank, Long> {
    Bank findBySwiftCode(String swiftCode);
    Bank findByName(String name);
}
