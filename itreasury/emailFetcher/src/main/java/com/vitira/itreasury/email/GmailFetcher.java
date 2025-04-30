package com.vitira.itreasury.email;

import com.vitira.itreasury.config.EmailConfiguration;

import javax.mail.*;
import java.util.Properties;

public class GmailFetcher extends AbstractEmailFetcher {

	@Override
	public void connect(EmailConfiguration config) throws MessagingException {

		String host = config.getHost();
		String username = config.getUsername();
		String password = config.getPassword();
		String protocol = config.getProtocol();

		Properties properties = new Properties();
		properties.put("mail.imap.host", host);
		properties.put("mail.imap.store.type", protocol);
		properties.put("mail.imap.port", config.getPort());
		properties.put("mail.imap.starttls.enable", config.getStartTlsEnable());
		properties.put("mail.imap.username", username);
		properties.put("mail.imap.password", password);
		properties.put("mail.email.provider", config.getProvider());

		// Get the Session object
		Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		// Create a store object and connect to the IMAP server
		store = session.getStore(protocol);
		store.connect(host, username, password);

		folder = store.getFolder("INBOX");
		folder.open(Folder.READ_ONLY);
	}

	@Override
	public void close() throws MessagingException {
		folder.close(false);
		store.close();
	}
}
