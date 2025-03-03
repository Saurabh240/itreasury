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
		for(String attachment : attachments) {
			mt940Parser.parseAndSave(attachment);
		}
	}

}
