package com.vitira.itreasury.service.erp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vitira.itreasury.odata.client.ERPClientApp;

@Service
public class ERPService {

	@Autowired
	protected ERPClientApp erpClientApp;

}