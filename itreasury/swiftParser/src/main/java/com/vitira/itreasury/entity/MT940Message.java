package com.vitira.itreasury.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

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

	@Column(name = "message_data", columnDefinition = "TEXT")
	private String messageData;
	@Column(name = "received_at")
	private LocalDateTime receivedAt;
	@Column(name = "created_at")
	private LocalDateTime createdAt;

	
	@OneToMany(mappedBy = "mt940Message", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> transactions;
	
}
