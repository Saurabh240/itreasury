package com.vitira.itreasury.config;

import java.io.IOException;
import java.util.Properties;

public class ConfigurationManager {
	private Configuration config;

	public ConfigurationManager(String configFilePath) throws IOException {
		this.config = new Configuration(configFilePath);
	}

	public Properties getProperties() {
		return config.getProperties();
	}

	public String getImapHost() {
		// Potential fallback or environment-based logic
		return config.getImapHost();
	}

	public String getMailStoreType() {
		return config.getMailStoreType();
	}

	public String getImapPort() {
		return config.getImapPort();
	}

	public String getEmailUsername() {
		return config.getEmailUsername();
	}

	public String getEmailPassword() {
		return config.getEmailPassword();
	}

	public String getEmailProvider() {
		return config.getEmailProvider();
	}
}
