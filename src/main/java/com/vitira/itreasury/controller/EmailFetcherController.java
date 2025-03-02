package com.vitira.itreasury.controller;

import com.vitira.itreasury.service.EmailFetcherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EmailFetcherController {

    @Autowired
    private EmailFetcherService emailService;

    @PostMapping("/fetchEmails")
    public String fetchEmails() {
        emailService.fetchEmailAttachments();
        return "Email attachments fetched and processed successfully!";
    }
}
