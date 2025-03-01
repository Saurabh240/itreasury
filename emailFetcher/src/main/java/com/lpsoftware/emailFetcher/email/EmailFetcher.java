package com.lpsoftware.emailFetcher.email;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.MessagingException;

import com.lpsoftware.emailFetcher.config.ConfigurationManager;
import com.lpsoftware.emailFetcher.model.Email;

public interface EmailFetcher {
    void connect(ConfigurationManager configManager) throws MessagingException;
    List<Email> fetchEmails(String subjectFilter, Date dateFilter) throws MessagingException, IOException;
    void close() throws MessagingException;
}
