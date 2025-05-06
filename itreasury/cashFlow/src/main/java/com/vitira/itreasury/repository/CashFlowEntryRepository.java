package com.vitira.itreasury.repository;

import com.vitira.itreasury.enums.PaymentType;
import com.vitira.itreasury.enums.Source;
import com.vitira.itreasury.entity.CashFlowEntry;
import com.vitira.itreasury.enums.Urgency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface CashFlowEntryRepository extends JpaRepository<CashFlowEntry, Long> {

    Optional<CashFlowEntry> findByInvoiceDateAndSourceAndPaymentType(
            LocalDate invoiceDate,
            Source source,
            PaymentType paymentType
    );

    Optional<CashFlowEntry> findByPaymentTypeAndUrgencyAndDueDate(
            PaymentType paymentType,
            Urgency urgency,
            LocalDate  dueDate
    );
}