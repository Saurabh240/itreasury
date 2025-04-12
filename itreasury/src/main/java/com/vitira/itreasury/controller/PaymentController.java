package com.vitira.itreasury.controller;

import com.vitira.itreasury.dto.PaymentRequest;
import com.vitira.itreasury.dto.PaymentResponse;
import com.vitira.itreasury.service.PaymentService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payments")
public class PaymentController {

	@Autowired
	PaymentService paymentService;

	@PostMapping
	public ResponseEntity<PaymentResponse> savePayment(@Valid @RequestBody PaymentRequest paymentRequest, Authentication connectedUser) {
		// First map the incoming request data to an internal Payment request DTO object
		PaymentRequest internalPaymentRequest = PaymentRequest.builder()
				.amount(paymentRequest.getAmount())
				.currency(paymentRequest.getCurrency())
				.description(paymentRequest.getDescription())
				.build();

		// Now pass this internal request object to the service layer for processing
		PaymentResponse paymentResponse = paymentService.savePayment(internalPaymentRequest, connectedUser);

		return ResponseEntity.ok(paymentResponse);
	}

	@GetMapping("/{id}")
	public ResponseEntity<PaymentResponse> getPaymentById(@PathVariable("id") Long id) {
		// Now pass this internal request object to the service layer for processing
		PaymentResponse paymentResponse = paymentService.findById(id);

		return ResponseEntity.ok(paymentResponse);
	}

	@GetMapping("/all")
	public ResponseEntity<List<PaymentResponse>> getAllPayments() {
		// Call the service layer to get all payments
		List<PaymentResponse> paymentResponses = paymentService.getAllPayments();

		return ResponseEntity.ok(paymentResponses);
	}

}