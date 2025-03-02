package com.vitira.itreasury.service;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vitira.itreasury.repository.EmailFetcherRepository;

@Service
public class EmailFetcherService {

	@Autowired
	private EmailFetcherRepository repository;

    @Autowired
    private MT940ParserService mt940Parser;

	public List<String> getAllAttachments() {

		return repository.getAllAttachments();
	}

	public void fetchEmailAttachments() {

		List<String> attachments = repository.getAllAttachments();
		attachments.forEach(this::processEmailAttachment);
		

	}

	private void processEmailAttachment(String attachment) {
		try {
			InputStream attachmentStream = new FileInputStream(attachment);
			String content = new String(attachmentStream.readAllBytes());
			mt940Parser.parseAndSave(content);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
