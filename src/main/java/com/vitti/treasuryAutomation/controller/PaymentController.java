package com.vitti.treasuryAutomation.controller;

import com.vitti.treasuryAutomation.dto.PaymentRequest;
import com.vitti.treasuryAutomation.dto.PaymentResponse;
import com.vitti.treasuryAutomation.service.PaymentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    PaymentService paymentService;

    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponse> getPaymentById(@PathVariable Long id) {
        // First map the incoming request data to an internal Payment request DTO object
        PaymentRequest internalPaymentRequest = new PaymentRequest();
        internalPaymentRequest.setPaymentId(id);

        //Now pass this internal request object to the service layer for processing
        PaymentResponse paymentResponse = paymentService.getPaymentDetailsById(internalPaymentRequest);

        return ResponseEntity.ok(paymentResponse);
    }

}