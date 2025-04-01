package com.vitira.itreasury.model;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class Email {
	private String subject;
	private String from;
	private Date sentDate;
	private String body;
	private List<String> attachments;

	public Email() {
		this.attachments = new ArrayList<>();
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public Date getSentDate() {
		return sentDate;
	}

	public void setSentDate(Date sentDate) {
		this.sentDate = sentDate;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public List<String> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<String> attachments) {
		this.attachments = attachments;
	}

	public void addAttachment(String attachment) {
		this.attachments.add(attachment);
	}
}
