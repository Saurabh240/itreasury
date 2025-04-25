package com.vitira.itreasury.entity;

import com.vitira.itreasury.enums.CategoryType;
import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TransactionCategory {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private CategoryType categoryType; // INFLOW or OUTFLOW
    private String categoryName; // e.g., localCollections, exportSales
    private String categoryDescription;

    private String identificationCodes; // Comma separated identification codes for matching
    private String transactionTypeCodes; // Comma separated transaction type codes for matching
    private String descKeywords; // Comma separated keywords for matching descriptions

}
