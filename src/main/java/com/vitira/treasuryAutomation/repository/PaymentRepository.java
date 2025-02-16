package com.vitira.treasuryAutomation.repository;

import com.vitira.treasuryAutomation.entity.PaymentEntity;
import com.vitira.treasuryAutomation.dto.PaymentRequest;
import org.springframework.stereotype.Repository;

@Repository
public class PaymentRepository {
    
    public PaymentEntity findPaymentById(PaymentRequest request) {
        
        PaymentEntity paymentModel = executeQuery(request);

        return paymentModel;
    }

    private PaymentEntity executeQuery(PaymentRequest request) {
        // This is a dummy method to simulate a database query
        PaymentEntity paymentModel = new PaymentEntity();
        paymentModel.setPaymentId(request.getPaymentId());
        paymentModel.setAmount(1000.00);
        paymentModel.setCurrency("INR");
        return paymentModel;
    }
}