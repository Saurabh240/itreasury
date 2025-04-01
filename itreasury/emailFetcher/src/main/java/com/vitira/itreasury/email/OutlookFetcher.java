package com.vitira.itreasury.email;

import java.util.Properties;

import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

import com.vitira.itreasury.config.ConfigurationManager;

public class OutlookFetcher extends AbstractEmailFetcher {

	@Override
	public void connect(ConfigurationManager configManager) throws MessagingException {
		Properties properties = configManager.getProperties();
		String host = configManager.getImapHost();
		String username = configManager.getEmailUsername();
		String password = configManager.getEmailPassword();
		String mailStoreType = configManager.getMailStoreType();

		// Get the Session object
		Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		// Create a store object and connect to the IMAP server
		store = session.getStore(mailStoreType);
		store.connect(host, username, password);

		folder = store.getFolder("INBOX");
		folder.open(Folder.READ_ONLY);
	}
}