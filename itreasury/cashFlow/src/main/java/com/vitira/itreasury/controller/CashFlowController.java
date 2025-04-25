package com.vitira.itreasury.controller;

import com.vitira.itreasury.dto.CashFlowResponse;
import com.vitira.itreasury.service.CashFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/cashFlow")
public class CashFlowController {

    @Autowired
    private CashFlowService cashFlowService;

    @GetMapping
    public CashFlowResponse getCashFlowData(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDateTime,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDateTime) {
        return cashFlowService.getCashFlowData( startDateTime, endDateTime);
    }
}