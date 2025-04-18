package com.vitira.itreasury.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequest {

    private String debitCreditMark;
    private BigDecimal amount;
    private String transactionType;
    private String supplementaryInfo;
    private String description;

}
