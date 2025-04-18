package com.vitira.itreasury.controller;

import com.vitira.itreasury.dto.TransactionDTO;
import com.vitira.itreasury.service.CashFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cashFlow")
public class CashFlowController {

    @Autowired
    private CashFlowService cashFlowService;

    @GetMapping
    public Map<String, List<TransactionDTO>> getCashFlowData() {
        return cashFlowService.getCashFlowData();
    }
}