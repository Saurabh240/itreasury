package com.vitti.treasuryAutomation.service;

import com.vitti.treasuryAutomation.dto.PaymentRequest;
import com.vitti.treasuryAutomation.dto.PaymentResponse;
import com.vitti.treasuryAutomation.entity.PaymentEntity;
import com.vitti.treasuryAutomation.repository.PaymentRepository;
// import com.vitti.treasuryAutomation.exception.PaymentNotFoundException;
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