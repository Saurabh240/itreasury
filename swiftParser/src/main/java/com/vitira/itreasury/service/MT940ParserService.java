package com.vitira.itreasury.service;

import com.prowidesoftware.swift.model.mt.mt9xx.MT940;
import com.vitira.itreasury.entity.BankAccount;
import com.vitira.itreasury.repository.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class MT940ParserService {

	@Autowired
	private BankAccountRepository bankAccountRepository;

	public void parseAndSave(String attachment) {

		BankAccount bankAccount = new BankAccount();
		
		File messageFile = new File(attachment);
		try {
			MT940 mt = MT940.parse(messageFile);
			System.out.println("Attachment: " + attachment);
			System.out.println("Sender: " + mt.getSender());
			System.out.println("Receiver: " + mt.getReceiver());
			System.out.println("ReferenceNumber: " + mt.getField20().getValue());
			System.out.println("BankCode: " + mt.getField25().getValue());
			System.out.println("AccountNumber: " + mt.getField25().getAccount());
			System.out.println("AccountHolder: " + mt.getField86().get(0));
			System.out.println("BankName: " + mt.getField25());
			System.out.println("Currency: " + mt.getField25());

		} catch (IOException e) {
			e.printStackTrace();
		}

//		
//
//		// Extract Account Identification (Account Number)
//		Pattern accountPattern = Pattern.compile(":25:([A-Z]{4})(\\d+)");
//		Matcher accountMatcher = accountPattern.matcher(mt940Message);
//		if (accountMatcher.find()) {
//			bankAccount.setBankCode(accountMatcher.group(1));
//			bankAccount.setAccountNumber(accountMatcher.group(2));
//		}
//
//		// Extract Account Holder and Bank Name from Narrative (86)
//		Pattern accountHolderPattern = Pattern.compile(":86:/NARR/([\\w\\s]+?)/BNK/([\\w\\s]+)");
//		Matcher accountHolderMatcher = accountHolderPattern.matcher(mt940Message);
//		if (accountHolderMatcher.find()) {
//			bankAccount.setAccountHolder(accountHolderMatcher.group(1).trim());
//			bankAccount.setBankName(accountHolderMatcher.group(2).trim());
//		}
//
//		// Extract Currency from Opening Balance (60F)
//		Pattern currencyPattern = Pattern.compile(":60F:C(\\d{6})([A-Z]{3})");
//		Matcher currencyMatcher = currencyPattern.matcher(mt940Message);
//		if (currencyMatcher.find()) {
//			bankAccount.setCurrency(currencyMatcher.group(2));
//		}

		// Save to the database
		bankAccountRepository.save(bankAccount);
	}
}
