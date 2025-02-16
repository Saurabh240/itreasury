package com.vitira.treasuryAutomation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.apache.olingo.odata2.api.ODataService;
import org.apache.olingo.odata2.api.ep.EntityProvider;
import org.apache.olingo.odata2.api.ep.EntityProviderWriteProperties;

@Service
public class ERPService {
    
    @Autowired
    private ODataService oDataService;

    @Autowired
    private EntityProvider entityProvider;

    @Autowired
    private EntityProviderWriteProperties entityProviderWriteProperties;

    public String getAccountsReceivable() {
        // Your logic to fetch Accounts Receivable data using OData
        // Example: Use oDataService to call the OData endpoint and parse the response
        return "AR Data";
    }

    public String getAccountsPayable() {
        // Your logic to fetch Accounts Payable data using OData
        // Example: Use oDataService to call the OData endpoint and parse the response
        return "AP Data";
    }
    
    // private PaymentResponse mapModelToDto() {
    	
    	
    // 	return null;
    // }

}