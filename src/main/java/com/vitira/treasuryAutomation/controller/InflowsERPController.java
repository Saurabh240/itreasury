package com.vitira.treasuryAutomation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vitira.treasuryAutomation.dto.InflowsRequest;
import com.vitira.treasuryAutomation.dto.InflowsResponse;
import com.vitira.treasuryAutomation.service.erp.InflowsERPService;

@RestController
@RequestMapping("/api/erp/inflows")
public class InflowsERPController {

    @Autowired
    private InflowsERPService erpService;

    @GetMapping
    public ResponseEntity<List<InflowsResponse>> getCashInflows() {
    	
    	List<InflowsResponse> inflowsResponses = erpService.getAllInflows();

        return ResponseEntity.ok(inflowsResponses);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<InflowsResponse> getCashInflowsById(@PathVariable Long id) {
    	
    	InflowsRequest request = new InflowsRequest();
    	request.setId(id);
    	
    	InflowsResponse inflowsResponse = erpService.getInflowsById(request);

        return ResponseEntity.ok(inflowsResponse);
    }
    
}
