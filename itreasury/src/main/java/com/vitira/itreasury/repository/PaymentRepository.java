package com.vitira.itreasury.repository;

import com.vitira.itreasury.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}