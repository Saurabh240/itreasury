package com.vitira.treasuryAutomation.service;

import com.vitira.treasuryAutomation.dto.PaymentRequest;
import com.vitira.treasuryAutomation.dto.PaymentResponse;
import com.vitira.treasuryAutomation.entity.PaymentEntity;
import com.vitira.treasuryAutomation.repository.PaymentRepository;
// import com.vitira.treasuryAutomation.exception.PaymentNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    
    @Autowired
    private PaymentRepository paymentRepository;

    public PaymentResponse getPaymentDetailsById(PaymentRequest internalPaymentObj) {
        
        // PaymentEntity payment = paymentRepository.findPaymentById(internalPaymentObj.getPaymentId()).orElseThrow(() -> new PaymentNotFoundException("Payment not found"));
        PaymentEntity payment = paymentRepository.findPaymentById(internalPaymentObj);
        
        // Now map it to PaymentResponse DTO
        PaymentResponse paymentResponse = mapModelToDto(payment);

        return paymentResponse;
    }
    
    private PaymentResponse mapModelToDto(PaymentEntity payment) {
    	
    	PaymentResponse paymentResponse = new PaymentResponse(
    			payment.getPaymentId(), 
    			payment.getAmount(), 
    			payment.getCurrency());
    	
    	return paymentResponse;
    }

}