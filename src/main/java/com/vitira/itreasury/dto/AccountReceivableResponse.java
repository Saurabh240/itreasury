package com.vitira.itreasury.dto;


public class AccountReceivableResponse {
	private Long paymentId;
    private Double amount;
    private String currency;

    public AccountReceivableResponse(Long id, Double amount, String currency) {
    	this.paymentId = id;
    	this.amount = amount;
    	this.currency = currency;
    }

	public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}