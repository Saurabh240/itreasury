package com.vitira.itreasury.service;

import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vitira.itreasury.service.MT940ParserService;
import com.vitira.itreasury.email.EmailClient;
import com.vitira.itreasury.model.Email;
import javax.mail.MessagingException;
import java.io.IOException;

@Service
public class EmailFetcherService {

	@Autowired
	private MT940ParserService mt940Parser;

	public void fetchEmailAttachments() {

		List<String> attachments = this.getAllAttachments();
		for (String attachment : attachments) {
			mt940Parser.parseAndSave(attachment);
		}
	}

	private List<String> getAllAttachments() {

		List<String> allAttachments = new ArrayList<>();
		EmailClient client;
		try {
			client = new EmailClient();
			List<Email> emails = client.executeFetch();
			for (Email email : emails) {
				allAttachments.addAll(email.getAttachments());
			}

		} catch (IOException | MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return allAttachments;

	}

}
