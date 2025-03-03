package com.vitira.itreasury.exception;

public class PaymentNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public PaymentNotFoundException(String message) {
		super(message);
	}

	public PaymentNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}