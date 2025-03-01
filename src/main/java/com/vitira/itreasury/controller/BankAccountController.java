package com.vitira.itreasury.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.vitira.itreasury.dto.BankAccountRequest;
import com.vitira.itreasury.dto.BankAccountResponse;
import com.vitira.itreasury.entity.BankAccount;
import com.vitira.itreasury.service.BankAccountService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/bankAccounts")
public class BankAccountController {

	@Autowired
	BankAccountService bankAccountService;

	@GetMapping("/{id}")
	public ResponseEntity<BankAccountResponse> getBankAccountById(@PathVariable("id") Long id) {
		// First map the incoming request data to an internal Payment request DTO object
		BankAccountRequest request = new BankAccountRequest();
		request.setId(id);

		// Now pass this internal request object to the service layer for processing
		BankAccountResponse response = bankAccountService.getBankAccountById(request);

		return ResponseEntity.ok(response);
	}


	@GetMapping
	public List<BankAccount> getAllBankAccounts() {
		return bankAccountService.getAllBankAccounts();
	}

	@PostMapping
	public BankAccount createBankAccount(@RequestBody BankAccount bankAccount) {
		return bankAccountService.saveBankAccount(bankAccount);
	}

	@DeleteMapping("/{id}")
	public void deleteBankAccount(@PathVariable Long id) {
		bankAccountService.deleteBankAccountById(id);
	}

}
