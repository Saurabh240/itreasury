package com.vitira.itreasury.controller;

import com.vitira.itreasury.service.EmailFetcherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailFetcherController {

	@Autowired
	private EmailFetcherService emailService;

	@GetMapping("/fetchEmails")
	public String fetchEmails() {
		emailService.fetchEmailAttachments();
		return "Email attachments fetched and processed successfully!";
	}
}
