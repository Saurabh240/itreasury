package com.vitti.treasuryAutomation.dto;

public class PaymentResponse {
    
    private Long paymentId;
    private Double amount;
    private String currency;
    
    public PaymentResponse(Long id, Double amount, String currency) {
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