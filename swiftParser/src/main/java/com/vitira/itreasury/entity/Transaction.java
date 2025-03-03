package com.vitira.itreasury.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "transaction")
public class Transaction {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "mt940_message_id", nullable = false)
    private MT940Message mt940Message;

    @Column(name = "VALUE_DATE")
    private LocalDateTime valueDate;
    @Column(name = "ENTRY_DATE")
    private LocalDateTime entryDate;
    @Column(name = "DEBITCREDIT_MARK")
    private String debitCreditMark;
    @Column(name = "FUNDS_CODE")
    private String fundsCode;
    @Column(name = "AMOUNT")
    private BigDecimal amount;
    @Column(name = "TRANSACTION_TYPE")
    private String transactionType;
    @Column(name = "IDENTIFICATION_CODE")
    private String identificationCode;
    @Column(name = "REFERENCE_FOR_THE_ACCOUNT_OWNER")
    private String referenceForAccOwner;
    @Column(name = "REFERENCE_OF_THE_ACCOUNT_SERVICING_INSTITUTION")
    private String refOfAccServingInstitution;
    @Column(name = "DESCRIPTION")
    private String description;
	
}
