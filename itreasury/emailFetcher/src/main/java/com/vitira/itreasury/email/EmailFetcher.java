package com.vitira.itreasury.email;

import com.vitira.itreasury.config.EmailConfiguration;
import com.vitira.itreasury.model.Email;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public interface EmailFetcher {
	void connect(EmailConfiguration config) throws MessagingException;

    List<Email> fetchEmails(String subjectFilter, Date dateFilter) throws MessagingException, IOException;

	void close() throws MessagingException;
}
