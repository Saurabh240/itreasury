package com.vitira.itreasury.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {

	@NotNull(message = "Amount cannot be null")
	private Double amount;
	@NotEmpty(message = "Currency cannot be empty")
	@NotNull(message = "Currency cannot be null")
	private String currency;
	private String description;

}