package com.vitira.itreasury.service;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.vitira.itreasury.entity.Bank;
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

	@Autowired
	private BankService bankService;

	@Autowired
	private TransactionCategorizationService transactionCategorizationService;

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
		// Extract the BIC (often found in the :25: tag)
        String swiftCode = mt.getSwiftMessage().getBlock1().getBIC().getBic8();
		String accountNumber = mt.getField25().getAccount();
		String accountHolder = "NA";

		BigDecimal closingBalance = mt.getField62F().getAmountAsBigDecimal();
		String currency = mt.getField62F().getCurrency(); // Closing available balance currency

		System.out.println("SwiftCode: " + swiftCode);
		System.out.println("AccountNumber: " + accountNumber);
		System.out.println("AccountHolder: " + accountHolder);
		System.out.println("closingBalance: " + closingBalance);
		System.out.println("Currency: " + currency);

		Bank bank = bankService.getOrCreateBank(swiftCode);

		BankAccount bankAccount = BankAccount.builder()
				.accountHolder(accountHolder)
				.accountNumber(accountNumber)
				.bank(bank)
				.balance(closingBalance)
				.currency(currency)
				.build();

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

		Field20 field20 = mt.getField20();
		String refNum = field20 != null ? field20.getReference() : null;

		MT940Message swiftMessage = MT940Message.builder()
				.transactionReferenceNumber(refNum)
				.bankAccount(bankAccount)
				.messageType("MT940")
				.messageData(mt.message())
				.receivedAt(LocalDateTime.now())
				.createdAt(LocalDateTime.now())
				.build();
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

				/*
				 * look ahead for field 86
				 */
				String desc = "NOT AVAILABLE"; // default value
				if (i + 1 < loop.size() && loop.getTag(i + 1).getName().equals("86")) {
					desc = loop.getTag(i + 1).getValue();
					System.out.println("Description: " + desc);
					i++;
				}
				Transaction transaction = buildTransaction(swiftMessage, field61);
				transaction.setDescription(desc);

				// Categorize transaction
				Transaction categorizedTransaction = transactionCategorizationService.categorizeTransaction(transaction);

				transactions.add(categorizedTransaction);
			}
		}

		// Save Transactions
		transactionRepository.saveAll(transactions);
	}

	Transaction buildTransaction(MT940Message swiftMessage, Field61 field61) {

		return Transaction.builder()
				.mt940Message(swiftMessage)
				.valueDate(DateTimeUtils.calendarToLocalDateTime(field61.getValueDateAsCalendar()))
				.entryDate(DateTimeUtils.calendarToLocalDateTime(field61.getEntryDateAsCalendar()))
				.debitCreditMark(field61.getDebitCreditMark())
				.fundsCode(field61.getFundsCode())
				.amount(field61.getAmountAsBigDecimal())
				.transactionType(field61.getTransactionType())
				.identificationCode(field61.getIdentificationCode())
				.referenceForAccOwner(field61.getReferenceForTheAccountOwner())
				.refOfAccServingInstitution(field61.getReferenceOfTheAccountServicingInstitution())
				.supplementaryInfo(field61.getSupplementaryDetails())
				.build();
	}

}
