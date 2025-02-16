package com.vitira.treasuryAutomation.controller;

import com.vitira.treasuryAutomation.service.ERPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/erp")
public class ERPController {

    @Autowired
    private ERPService erpService;

    @GetMapping("/accountsReceivable")
    public String getAccountsReceivable() {
        return erpService.getAccountsReceivable();
    }

    @GetMapping("/accountsPayable")
    public String getAccountsPayable() {
        return erpService.getAccountsPayable();
    }
}
