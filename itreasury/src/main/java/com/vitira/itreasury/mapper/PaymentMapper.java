package com.vitira.itreasury.mapper;

import com.vitira.itreasury.dto.PaymentRequest;
import com.vitira.itreasury.dto.PaymentResponse;
import com.vitira.itreasury.entity.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentMapper {

    public Payment toPayment(PaymentRequest request) {
        return Payment.builder()
                .amount(request.getAmount())
                .currency(request.getCurrency())
                .description(request.getDescription())
                .createdDate(LocalDateTime.now())
                .build();
    }

    public PaymentResponse toPaymentResponse(Payment payment) {
        return PaymentResponse.builder()
                .amount(payment.getAmount())
                .currency(payment.getCurrency())
                .description(payment.getDescription())
                .build();
    }

}
