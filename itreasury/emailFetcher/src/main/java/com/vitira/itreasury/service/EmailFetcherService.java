package com.vitira.itreasury.service;

import com.vitira.itreasury.email.EmailClient;
import com.vitira.itreasury.model.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmailFetcherService {

	@Autowired
	private MT940ParserService mt940Parser;

	@Autowired
	private EmailClient emailClient;

	public void fetchEmailAttachments() {

		List<String> attachments = this.getAllAttachments();
		for (String attachment : attachments) {
			mt940Parser.parseAndSave(attachment);
		}
	}

	private List<String> getAllAttachments() {

		List<String> allAttachments = new ArrayList<>();
		try {
			List<Email> emails = emailClient.executeFetch();
			for (Email email : emails) {
				allAttachments.addAll(email.getAttachments());
			}

		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return allAttachments;

	}

}
