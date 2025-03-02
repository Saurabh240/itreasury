//package com.vitira.itreasury.config;
//
//import java.util.Properties;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class EmailConfiguration {
//
//	@Value("${spring.mail.host}")
//	private String host;
//
//	@Value("${spring.mail.port}")
//	private String port;
//
//	@Value("${spring.mail.username}")
//	private String username;
//
//	@Value("${spring.mail.password}")
//	private String password;
//
//	@Value("${spring.mail.protocol}")
//	private String protocol;
//
//	@Value("${spring.mail.properties.mail.imap.ssl.enable}")
//	private String sslEnable;
//
//	@Value("${spring.mail.properties.mail.imap.starttls.enable}")
//	private String starttlsEnable;
//
//	@Value("${spring.mail.properties.mail.imap.auth}")
//	private String auth;
//
//	@Value("${spring.mail.properties.mail.debug}")
//	private String debug;
//
//	private Properties properties;
//
//	public EmailConfiguration() {
//		properties = new Properties();
//		properties.put("mail.imap.host", host);
//		properties.put("mail.imap.port", port);
//		properties.put("mail.imap.ssl.enable", sslEnable);
//		properties.put("mail.imap.auth", auth);
//
////		properties.put("username", username);
////		properties.put("password", password);
////		properties.put("host", host);
////		properties.put("host", host);
////		properties.put("host", host);
////		properties.put("host", host);
//		
//		printMailConfig();
//	}
//
//	public String getHost() {
//		return host;
//	}
//
//	public String getPort() {
//		return port;
//	}
//
//	public String getUsername() {
//		return username;
//	}
//
//	public String getPassword() {
//		return password;
//	}
//
//	public String getProtocol() {
//		return protocol;
//	}
//
//	public String getSslEnable() {
//		return sslEnable;
//	}
//
//	public String getStarttlsEnable() {
//		return starttlsEnable;
//	}
//
//	public String getAuth() {
//		return auth;
//	}
//
//	public String getDebug() {
//		return debug;
//	}
//
//	public Properties getProperties() {
//		return properties;
//	}
//
//	public void printMailConfig() {
//		System.out.println("Mail Host: " + host);
//		System.out.println("Mail Port: " + port);
//		System.out.println("Mail Username: " + username);
//		System.out.println("Mail Password: " + password);
//		System.out.println("Mail Protocol: " + protocol);
//	}
//
//}
