package com.vitira.itreasury.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "BankAccount")
public class BankAccount {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String bankCode;
	@Column(unique=true, nullable = false)
    private String accountNumber;
    private String accountHolder;
    private String currency;
    private String bankName;
    private BigDecimal balance;
    
    @OneToMany(mappedBy = "bankAccount", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MT940Message> mt940Messages;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @ManyToOne
    @JoinColumn(name = "bank_id")
    private Bank bank;
    
}
