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
import com.vitira.treasuryAutomation.dto.OutflowsRequest;
import com.vitira.treasuryAutomation.dto.OutflowsResponse;
import com.vitira.treasuryAutomation.service.erp.InflowsERPService;
import com.vitira.treasuryAutomation.service.erp.OutflowsERPService;

@RestController
@RequestMapping("/api/erp/outflows")
public class OutflowsERPController {

    @Autowired
    private OutflowsERPService erpService;

    @GetMapping
    public ResponseEntity<List<OutflowsResponse>> getCashInflows() {
    	
    	List<OutflowsResponse> outflowsResponses = erpService.getAllOutflows();

        return ResponseEntity.ok(outflowsResponses);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<OutflowsResponse> getCashInflowsById(@PathVariable Long id) {
    	
    	OutflowsRequest request = new OutflowsRequest();
    	request.setId(id);
    	
    	OutflowsResponse outflowsResponse = erpService.getInflowsById(request);

        return ResponseEntity.ok(outflowsResponse);
    }
    
}
