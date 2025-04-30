package com.vitira.itreasury.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class EmailConfiguration {

	@Value("${spring.mail.imap.host}")
	private String host;

	@Value("${spring.mail.imap.port}")
	private String port;

	@Value("${spring.mail.imap.protocol}")
	private String protocol;

	@Value("${spring.mail.imap.username}")
	private String username;

	@Value("${spring.mail.imap.password}")
	private String password;

	@Value("${spring.mail.imap.starttls.enable}")
	private String startTlsEnable;

	@Value("${spring.mail.email.provider}")
	private String provider;

}
