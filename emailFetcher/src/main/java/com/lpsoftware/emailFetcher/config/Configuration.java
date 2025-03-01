package com.lpsoftware.emailFetcher.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Configuration {
    private Properties properties;

    public Configuration(String configFilePath) throws IOException {
        properties = new Properties();
        FileInputStream inputStream = new FileInputStream(configFilePath);
        properties.load(inputStream);
        inputStream.close();
    }

    public Properties getProperties() {
        return properties;
    }

    public String getImapHost() {
        return properties.getProperty("mail.imap.host");
    }
    
    public String getMailStoreType() {
        return properties.getProperty("mail.imap.store.type");
    }

    public String getImapPort() {
        return properties.getProperty("mail.imap.port", "993");
    }

    public String getEmailUsername() {
        return properties.getProperty("mail.imap.username");
    }

    public String getEmailPassword() {
        String passwordFromEnv = System.getenv("G_PASS");
        if (passwordFromEnv != null && !passwordFromEnv.isEmpty()) {
            return passwordFromEnv;
        } else {
            return properties.getProperty("mail.imap.password");
        }
    }

    public String getEmailProvider() {
        return properties.getProperty("mail.email.provider");
    }
}
