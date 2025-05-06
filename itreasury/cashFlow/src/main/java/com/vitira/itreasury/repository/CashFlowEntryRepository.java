package com.vitira.itreasury.repository;

import com.vitira.itreasury.enums.Source;
import com.vitira.itreasury.entity.CashFlowEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface CashFlowEntryRepository extends JpaRepository<CashFlowEntry, Long> {

    Optional<CashFlowEntry> findByInvoiceDateAndSourceAndCategory(
            LocalDate invoiceDate,
            Source source,
            String category
    );
}