package com.vitira.itreasury.service;

import com.prowidesoftware.swift.model.SwiftTagListBlock;
import com.prowidesoftware.swift.model.Tag;
import com.prowidesoftware.swift.model.field.Field61;
import com.prowidesoftware.swift.model.mt.mt9xx.MT940;

import com.vitira.itreasury.entity.BankAccount;
import com.vitira.itreasury.entity.MT940Message;
import com.vitira.itreasury.entity.Transaction;
import com.vitira.itreasury.repository.BankAccountRepository;
import com.vitira.itreasury.repository.MT940MessageRepository;
import com.vitira.itreasury.repository.TransactionRepository;
import com.vitira.itreasury.helpers.DateTimeUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
			e.printStackTrace();
		}
	}
	
	private void saveBankAccount(MT940 mt) {
		
		// TODO: Not sure how to pick this one
		String bankCode = "NA";
		String accountNumber = mt.getField25().getAccount();
		// TODO: Not sure how to pick this one
		String accountHolder = "NA";
		// TODO: Not sure how to pick this one
		String bankName = "NA";
		// Closing available balance currency
		String currency = mt.getField62F().getCurrency();
		System.out.println("BankCode: " + bankCode);
		System.out.println("AccountNumber: " + accountNumber);
		System.out.println("AccountHolder: " + accountHolder);
		System.out.println("BankName: " + bankName);
		System.out.println("Currency: " + currency);
		
		BankAccount bankAccount = new BankAccount();
		bankAccount.setBankCode(bankCode);
		bankAccount.setAccountHolder(accountHolder);
		bankAccount.setAccountNumber(accountNumber);
		bankAccount.setBankName(bankName);
		bankAccount.setCurrency(currency);
		
		// Save to the database
		bankAccountRepository.save(bankAccount);
		
		// Save MT940 message
		saveMT940Message(mt, bankAccount);
	}
	
	private void saveMT940Message(MT940 mt, BankAccount bankAccount) {
		// Save Swift Message
        MT940Message swiftMessage = new MT940Message();
        swiftMessage.setBankAccount(bankAccount);
        swiftMessage.setMessageType("MT940");
        swiftMessage.setMessageData(mt.message());
        swiftMessage.setReceivedAt(LocalDateTime.now());
        swiftMessage.setCreatedAt(LocalDateTime.now());
        swiftMessage = mt940MessageRepository.save(swiftMessage);
        
        saveTransactions(mt, swiftMessage);
	}
	
	private void saveTransactions(MT940 mt, MT940Message swiftMessage) {
		// Parse Transactions
        List<Transaction> transactions = new ArrayList<>();
        
        Tag start = mt.getSwiftMessage().getBlock4().getTagByNumber(60);
        Tag end = mt.getSwiftMessage().getBlock4().getTagByNumber(62);
        SwiftTagListBlock loop = mt.getSwiftMessage().getBlock4().getSubBlock(start, end);
        for (int i = 0; i < loop.size(); i++) {
            Tag t = loop.getTag(i);
            if (t.getName().equals("61")) {
                Field61 field61 = (Field61) t.asField();
                
                LocalDateTime valueDate = DateTimeUtils.calendarToLocalDateTime(field61.getValueDateAsCalendar());
            	LocalDateTime entryDate = DateTimeUtils.calendarToLocalDateTime(field61.getEntryDateAsCalendar());
            	String dcMark = field61.getDebitCreditMark();
            	String fundsCode = field61.getFundsCode();
            	BigDecimal amount = field61.getAmountAsBigDecimal();
            	String trxType = field61.getTransactionType();
            	String identificationCode = field61.getIdentificationCode();
            	String referenceForAccOwner = field61.getReferenceForTheAccountOwner();
            	String referenceOfTheAccountServicingInstitution = field61.getReferenceOfTheAccountServicingInstitution();
            	String supplementaryDetails = field61.getSupplementaryDetails();
            	String desc = "";
                
                System.out.println("---------------------------");
                System.out.println("Amount: " + amount);
                System.out.println("Transaction Type: " + trxType);
                System.out.println("Identification: "+ identificationCode); //since version 7.8
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
