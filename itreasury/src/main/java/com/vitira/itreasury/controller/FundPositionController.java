package com.vitira.itreasury.controller;

import com.vitira.itreasury.dto.FundPositionDTO;
import com.vitira.itreasury.service.FundPositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fundPosition")
public class FundPositionController {
    @Autowired
    private FundPositionService fundPositionService;

    @GetMapping("/{companyId}")
    public FundPositionDTO getFundPosition(@PathVariable Long companyId) {
        return fundPositionService.getFundPosition(companyId);
    }
}
