package com.vitira.itreasury.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "transaction")
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "mt940_message_id", nullable = false)
	private MT940Message mt940Message;

	private LocalDateTime valueDate;
	private LocalDateTime entryDate;
	private String debitCreditMark;
	private String fundsCode;
	private BigDecimal amount;
	private String currency;
	private String transactionType;
	private String identificationCode;
	private String referenceForAccOwner;
	private String refOfAccServingInstitution;
	private String transactionTypeCode;
	private String supplementaryInfo;
	private String description;

	@ManyToOne
	private TransactionCategory category; // Assigned category

}
