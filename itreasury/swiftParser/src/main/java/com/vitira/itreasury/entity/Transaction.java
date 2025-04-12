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
import lombok.*;

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
	private String transactionType;
	private String identificationCode;
	private String referenceForAccOwner;
	private String refOfAccServingInstitution;
	private String description;

}
