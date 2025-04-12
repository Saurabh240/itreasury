package com.vitira.itreasury.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "MT940Message")
public class MT940Message {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Column(name = "transaction_reference_number", unique = true, nullable = false)
	private String transactionReferenceNumber;
	
	@ManyToOne
    @JoinColumn(name = "bank_account_id", nullable = false)
    private BankAccount bankAccount;
	
	@Column(name = "message_type")
	private String messageType;
	
	@Lob
	@Column(name = "message_data")
	private String messageData;
	@Column(name = "received_at")
	private LocalDateTime receivedAt;
	@Column(name = "created_at")
	private LocalDateTime createdAt;

	
	@OneToMany(mappedBy = "mt940Message", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> transactions;
	
}
