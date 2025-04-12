package com.vitira.itreasury.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FundPositionDTO {
    private Long companyId;
    private String companyName;
    private BigDecimal totalFunds;
    private List<BankwiseFundDTO> bankwiseFunds;
}
