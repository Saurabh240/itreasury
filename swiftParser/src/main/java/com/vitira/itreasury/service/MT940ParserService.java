package com.vitira.itreasury.service;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.prowidesoftware.swift.model.SwiftTagListBlock;
import com.prowidesoftware.swift.model.Tag;
import com.prowidesoftware.swift.model.field.Field20;
import com.prowidesoftware.swift.model.field.Field61;
import com.prowidesoftware.swift.model.mt.mt9xx.MT940;
import com.vitira.itreasury.entity.BankAccount;
import com.vitira.itreasury.entity.MT940Message;
import com.vitira.itreasury.entity.Transaction;
import com.vitira.itreasury.helpers.DateTimeUtils;
import com.vitira.itreasury.repository.BankAccountRepository;
import com.vitira.itreasury.repository.MT940MessageRepository;
import com.vitira.itreasury.repository.TransactionRepository;

@Service
public class MT940ParserService {

	@Autowired
	private BankAccountRepository bankAccountRepository;

	@Autowired
	private MT940MessageRepository mt940MessageRepository;

	@Autowired
	private TransactionRepository transactionRepository;

	public void parseAndSave(String attachment) {

		System.out.println("Parsing : " + attachment);
		File messageFile = new File(attachment);
		try {
			MT940 mt = MT940.parse(messageFile);
			saveBankAccount(mt);
		} catch (IOException e) {
			System.err.println("Error parsing message file: " + e.getMessage());
		}
	}

	private void saveBankAccount(MT940 mt) {

		System.out.println("Saving Bank Account details.");

		String bankCode = "NA";
		String accountNumber = mt.getField25().getAccount();
		String accountHolder = "NA";
		String bankName = "NA";

		BigDecimal closingBalance = mt.getField62F().getAmountAsBigDecimal();
		String currency = mt.getField62F().getCurrency(); // Closing available balance currency

		System.out.println("BankCode: " + bankCode);
		System.out.println("AccountNumber: " + accountNumber);
		System.out.println("AccountHolder: " + accountHolder);
		System.out.println("BankName: " + bankName);
		System.out.println("closingBalance: " + closingBalance);
		System.out.println("Currency: " + currency);

		BankAccount bankAccount = new BankAccount();
		bankAccount.setBankCode(bankCode);
		bankAccount.setAccountHolder(accountHolder);
		bankAccount.setAccountNumber(accountNumber);
		bankAccount.setBankName(bankName);
		bankAccount.setBalance(closingBalance);
		bankAccount.setCurrency(currency);

		try {
			// Save to the database
			bankAccountRepository.save(bankAccount);
		} catch (DataIntegrityViolationException e) {
			System.out.println("Duplicate transaction reference number.");
			bankAccount = bankAccountRepository.findByAccountNumber(accountNumber);
		}

		// Note we will store the message even if the duplicate account number exception
		// is thrown
		// But same is not true for SwiftMessage because same transactionReference
		// number means same duplicate message
		saveMT940Message(mt, bankAccount);

	}

	private void saveMT940Message(MT940 mt, BankAccount bankAccount) {
		// Save Swift Message
		System.out.println("Saving Swift message details.");
		MT940Message swiftMessage = new MT940Message();

		Field20 field20 = mt.getField20();
		String refNum = field20 != null ? field20.getReference() : null;

		swiftMessage.setTransactionReferenceNumber(refNum);
		swiftMessage.setBankAccount(bankAccount);
		swiftMessage.setMessageType("MT940");
		swiftMessage.setMessageData(mt.message());
		swiftMessage.setReceivedAt(LocalDateTime.now());
		swiftMessage.setCreatedAt(LocalDateTime.now());
		try {
			swiftMessage = mt940MessageRepository.save(swiftMessage);
			saveTransactions(mt, swiftMessage);
		} catch (DataIntegrityViolationException e) {
			System.out.println("Duplicate account number.");
		}
	}

	private void saveTransactions(MT940 mt, MT940Message swiftMessage) {
		// Parse Transactions
		System.out.println("Saving transactions.");
		List<Transaction> transactions = new ArrayList<>();

		Tag start = mt.getSwiftMessage().getBlock4().getTagByNumber(60);
		Tag end = mt.getSwiftMessage().getBlock4().getTagByNumber(62);
		SwiftTagListBlock loop = mt.getSwiftMessage().getBlock4().getSubBlock(start, end);
		for (int i = 0; i < loop.size(); i++) {
			Tag t = loop.getTag(i);
			if (t.getName().equals("61")) {
				Field61 field61 = (Field61) t.asField();

				Calendar calValueDate = field61.getValueDateAsCalendar();
				Calendar calEntryDate = field61.getEntryDateAsCalendar();

				System.out.println(calValueDate);
				System.out.println(calEntryDate);

				LocalDateTime valueDate = DateTimeUtils.calendarToLocalDateTime(calValueDate);
				LocalDateTime entryDate = DateTimeUtils.calendarToLocalDateTime(calEntryDate);
				String dcMark = field61.getDebitCreditMark();
				String fundsCode = field61.getFundsCode();
				BigDecimal amount = field61.getAmountAsBigDecimal();
				String trxType = field61.getTransactionType();
				String identificationCode = field61.getIdentificationCode();
				String referenceForAccOwner = field61.getReferenceForTheAccountOwner();
				String referenceOfTheAccountServicingInstitution = field61
						.getReferenceOfTheAccountServicingInstitution();
				String supplementaryDetails = field61.getSupplementaryDetails();
				String desc = "";

				System.out.println("---------------------------");
				System.out.println("Amount: " + amount);
				System.out.println("Transaction Type: " + trxType);
				System.out.println("Identification: " + identificationCode); // since version 7.8
				System.out.println("Reference Acc Owner: " + referenceForAccOwner);
				/*
				 * look ahead for field 86
				 */
				if (i + 1 < loop.size() && loop.getTag(i + 1).getName().equals("86")) {
					desc = loop.getTag(i + 1).getValue();
					System.out.println("Description: " + desc);
					i++;
				}

				Transaction transaction = new Transaction();
				transaction.setMt940Message(swiftMessage);
				transaction.setValueDate(valueDate);
				transaction.setEntryDate(entryDate);
				transaction.setDebitCreditMark(dcMark);
				transaction.setFundsCode(fundsCode);
				transaction.setAmount(amount);
				transaction.setTransactionType(trxType);
				transaction.setIdentificationCode(identificationCode);
				transaction.setIdentificationCode(referenceForAccOwner);
				transaction.setIdentificationCode(referenceOfTheAccountServicingInstitution);
				transaction.setIdentificationCode(supplementaryDetails);
				transaction.setDescription(desc);
				transactions.add(transaction);
			}
		}

		// Save Transactions
		transactionRepository.saveAll(transactions);
	}

}
