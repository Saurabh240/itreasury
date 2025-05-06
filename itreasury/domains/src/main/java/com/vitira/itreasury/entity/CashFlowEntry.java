package com.vitira.itreasury.entity;

import com.vitira.itreasury.enums.CategoryType;
import com.vitira.itreasury.enums.Urgency;
import com.vitira.itreasury.enums.Source;
import com.vitira.itreasury.enums.PaymentType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(
        name = "cash_flow_entry",
        indexes = @Index(name = "IDX_cashflow_business_date", columnList = "business_date")
)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CashFlowEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "source", nullable = false, length = 10)
    private Source source;

    @Enumerated(EnumType.STRING)
    @Column(name = "category_type", length = 50)
    private CategoryType categoryType;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_type", length = 50)
    private PaymentType paymentType;

    @Enumerated(EnumType.STRING)
    @Column(name = "urgency", length = 50)
    private Urgency urgency;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal amount;

    @Column(name = "doc_curr_value", precision = 19, scale = 4)
    private String docCurrValue;

    @Column(name = "supplier_code", precision = 19, scale = 4)
    private String supplierCode;

    @Column(name = "text", precision = 19, scale = 4)
    private String text;

    @Column(name = "invoice_date")
    private LocalDate invoiceDate;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = updatedAt = Instant.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = Instant.now();
    }
}

