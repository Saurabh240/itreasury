package com.vitira.itreasury.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vitira.itreasury.dto.BankAccountRequest;
import com.vitira.itreasury.dto.BankAccountResponse;
import com.vitira.itreasury.entity.BankAccount;
import com.vitira.itreasury.repository.BankAccountRepository;

import java.util.List;

@Service
public class BankAccountService {

	@Autowired
	private BankAccountRepository repository;

    public List<BankAccount> getAllBankAccounts(Long companyId) {
        return repository.findByCompanyId(companyId);
    }

	public BankAccount saveBankAccount(BankAccount bankAccount) {
		return repository.save(bankAccount);
	}

	public BankAccountResponse getBankAccountById(BankAccountRequest request) {

		BankAccount bankAccount = repository.findById(request.getId()).orElse(null);

		if (bankAccount != null) {
			BankAccountResponse response = new BankAccountResponse();
			response.setId(bankAccount.getId());
			response.setBankCode(bankAccount.getBankCode());
			response.setAccountNumber(bankAccount.getAccountNumber());
			response.setAccountHolder(bankAccount.getAccountHolder());
			response.setCurrency(bankAccount.getCurrency());
			response.setBankName(bankAccount.getBankName());

			return response;
		}

		return null;
	}

	public void deleteBankAccountById(Long id) {
		repository.deleteById(id);
	}
}
