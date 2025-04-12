package com.vitira.itreasury.email;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.io.IOException;

import javax.mail.MessagingException;

import com.vitira.itreasury.config.ConfigurationManager;
import com.vitira.itreasury.model.Email;

public class EmailClient {
	private final EmailFetcher fetcher;
	private final ConfigurationManager configManager;

	public EmailClient() throws IOException {
		this.configManager = new ConfigurationManager("src/main/resources/config.properties");
		String providerStr = configManager.getEmailProvider();
		EmailProvider provider = EmailProvider.valueOf(providerStr.toUpperCase());
		this.fetcher = EmailFetcherFactory.createEmailFetcher(provider);
	}

	public List<Email> executeFetch() throws MessagingException {
		List<Email> emails = new ArrayList<>();
		try {
			fetcher.connect(configManager);

			// Create search terms
			String subjectFilter = "MT940 Statement for Account";
			Date dateFilter = new Date(System.currentTimeMillis() - (2L * 24 * 60 * 60 * 1000)); // Emails from the last
																									// 7 days

			emails = fetcher.fetchEmails(subjectFilter, dateFilter);

			if (emails.isEmpty()) {
				System.out.println("No Emails data present for the day yet...");
			}

			for (Email email : emails) {
				System.out.println("Subject: " + email.getSubject());
				System.out.println("From: " + email.getFrom());
				System.out.println("Sent Date: " + email.getSentDate());
				System.out.println("Body: " + email.getBody());
				System.out.println("Attachments: " + email.getAttachments());
				System.out.println("-------------------------------------------");
			}

		} catch (MessagingException | IOException e) {
			System.err.println("Error occurred: " + e.getMessage());
			e.printStackTrace();
		} finally {
			fetcher.close();
		}
		return emails;
	}
}
