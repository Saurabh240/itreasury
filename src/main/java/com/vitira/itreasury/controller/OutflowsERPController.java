//package com.vitira.itreasury.controller;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.vitira.itreasury.dto.InflowsRequest;
//import com.vitira.itreasury.dto.InflowsResponse;
//import com.vitira.itreasury.dto.OutflowsRequest;
//import com.vitira.itreasury.dto.OutflowsResponse;
//import com.vitira.itreasury.service.erp.InflowsERPService;
//import com.vitira.itreasury.service.erp.OutflowsERPService;
//
//@RestController
//@RequestMapping("/api/erp/outflows")
//public class OutflowsERPController {
//
//	@Autowired
//	private OutflowsERPService erpService;
//
//	@GetMapping
//	public ResponseEntity<List<OutflowsResponse>> getCashInflows() {
//
//		List<OutflowsResponse> outflowsResponses = erpService.getAllOutflows();
//
//		return ResponseEntity.ok(outflowsResponses);
//	}
//
//	@GetMapping("/{id}")
//	public ResponseEntity<OutflowsResponse> getCashInflowsById(@PathVariable Long id) {
//
//		OutflowsRequest request = new OutflowsRequest();
//		request.setId(id);
//
//		OutflowsResponse outflowsResponse = erpService.getInflowsById(request);
//
//		return ResponseEntity.ok(outflowsResponse);
//	}
//
//}
