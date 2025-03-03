package com.vitira.itreasury.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Setter
@Getter
@Entity
@Table(name = "BankAccount")
public class BankAccount {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@Column(name = "bank_code")
    private String bankCode;
    
	@Column(name = "account_number")
    private String accountNumber;
    
    @Column(name = "account_holder")
    private String accountHolder;
    
    @Column(name = "currency")
    private String currency;
    
    @Column(name = "bank_name")
    private String bankName;
    
    @Column(name = "closing_balance")
    private BigDecimal closingBalance;
    
    @OneToMany(mappedBy = "bankAccount", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MT940Message> mt940Messages;
    
}
