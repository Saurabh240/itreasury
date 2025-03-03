package com.vitira.itreasury.email;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Part;
import javax.mail.Store;
import javax.mail.internet.MimeMultipart;
import javax.mail.search.AndTerm;
import javax.mail.search.ComparisonTerm;
import javax.mail.search.SentDateTerm;
import javax.mail.search.SearchTerm;
import javax.mail.search.SubjectTerm;

import com.vitira.itreasury.config.ConfigurationManager;
import com.vitira.itreasury.model.Email;

public abstract class AbstractEmailFetcher implements EmailFetcher {
	protected Store store;
	protected Folder folder;

	@Override
	public void connect(ConfigurationManager configManager) throws MessagingException {
		// Implementation will be provided by subclasses
	}

	@Override
	public List<Email> fetchEmails(String subjectFilter, Date dateFilter) throws MessagingException, IOException {
		List<Email> emailList = new ArrayList<>();

		// Create search terms
		SubjectTerm subjectTerm = new SubjectTerm(subjectFilter);
		SearchTerm dateTerm = new SentDateTerm(ComparisonTerm.GT, dateFilter);
		// Combine search terms using AndTerm
		SearchTerm searchTerm = new AndTerm(subjectTerm, dateTerm);

		Message[] messages = folder.search(searchTerm);

		for (Message message : messages) {
			Email email = new Email();
			email.setSubject(message.getSubject());
			email.setFrom(message.getFrom()[0].toString());
			email.setSentDate(message.getSentDate());
			email.setBody(getTextFromMessage(message));

			// Process the message content and extract attachments
			if (message.getContent() instanceof MimeMultipart) {
				MimeMultipart multipart = (MimeMultipart) message.getContent();
				for (int i = 0; i < multipart.getCount(); i++) {
					BodyPart bodyPart = multipart.getBodyPart(i);
					if (Part.ATTACHMENT.equalsIgnoreCase(bodyPart.getDisposition())) {
						// Save the attachment to a file
						saveAttachment(bodyPart);
					}
				}
			}

			emailList.add(email);
		}

		return emailList;
	}

	@Override
	public void close() throws MessagingException {
		folder.close(false);
		store.close();
	}

	protected static String getTextFromMessage(Message message) throws IOException, MessagingException {
		if (message.isMimeType("text/plain")) {
			return message.getContent().toString();
		} else if (message.isMimeType("multipart/*")) {
			MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
			return getTextFromMimeMultipart(mimeMultipart);
		}
		return "";
	}

	protected static String getTextFromMimeMultipart(MimeMultipart mimeMultipart)
			throws IOException, MessagingException {
		String result = "";
		int count = mimeMultipart.getCount();
		for (int i = 0; i < count; i++) {
			BodyPart bodyPart = mimeMultipart.getBodyPart(i);
			if (bodyPart.isMimeType("text/plain")) {
				result = result + "\n" + bodyPart.getContent();
				break; // If we find plain text, return it and ignore the rest
			} else if (bodyPart.isMimeType("text/html")) {
				String html = (String) bodyPart.getContent();
				result = result + "\n" + org.jsoup.Jsoup.parse(html).text(); // Use Jsoup to convert HTML to plain text
			} else if (bodyPart.getContent() instanceof MimeMultipart) {
				result = result + getTextFromMimeMultipart((MimeMultipart) bodyPart.getContent());
			}
		}
		return result;
	}

	protected static void saveAttachment(BodyPart bodyPart) throws IOException, MessagingException {
		InputStream is = bodyPart.getInputStream();
		File file = new File(bodyPart.getFileName());
		try (FileOutputStream fos = new FileOutputStream(file)) {
			byte[] buf = new byte[4096];
			int bytesRead;
			while ((bytesRead = is.read(buf)) != -1) {
				fos.write(buf, 0, bytesRead);
			}
		}
		System.out.println("Attachment saved: " + file.getAbsolutePath());
	}
}