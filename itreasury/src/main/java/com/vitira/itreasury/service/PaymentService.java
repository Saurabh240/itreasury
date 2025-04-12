package com.vitira.itreasury.service;

import com.vitira.itreasury.dto.PaymentRequest;
import com.vitira.itreasury.dto.PaymentResponse;
import com.vitira.itreasury.entity.Payment;
import com.vitira.itreasury.entity.UserEntity;
import com.vitira.itreasury.exception.PaymentNotFoundException;
import com.vitira.itreasury.mapper.PaymentMapper;
import com.vitira.itreasury.repository.PaymentRepository;
// import com.vitira.itreasury.exception.PaymentNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentService {

	private final PaymentMapper paymentMapper;

	@Autowired
	private PaymentRepository paymentRepository;

	@Transactional
	public PaymentResponse savePayment(PaymentRequest paymentRequest, Authentication connectedUser) {
		// Map the incoming request data to an internal Payment entity object
		Payment payment = paymentMapper.toPayment(paymentRequest);
		UserEntity user = (UserEntity) connectedUser.getPrincipal();
		payment.setCreatedBy(user.getUsername());

		// Save the payment entity to the database
		Payment savedPayment = paymentRepository.save(payment);

		// Map the saved Payment entity to a PaymentResponse DTO
		return paymentMapper.toPaymentResponse(savedPayment);
	}

	public PaymentResponse findById(Long id) {
		return paymentRepository.findById(id)
				.map(paymentMapper::toPaymentResponse)
				.orElseThrow(() -> new PaymentNotFoundException("Payment not found with ID: " + id));
	}

	public List<PaymentResponse> getAllPayments() {
		List<Payment> payments = paymentRepository.findAll();
		// Map each PaymentEntity to PaymentResponse
        return payments.stream()
                .map(paymentMapper::toPaymentResponse)
                .collect(Collectors.toList());
	}

}