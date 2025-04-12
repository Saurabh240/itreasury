package com.vitira.itreasury.dto;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {

	private Double amount;
	private String currency;
	private String description;

}