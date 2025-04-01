package com.vitira.itreasury.repository;

import com.vitira.itreasury.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
