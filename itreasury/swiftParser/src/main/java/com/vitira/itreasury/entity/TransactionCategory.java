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

    private String supplementaryKeywords; // Comma separated keywords for matching supplementary information
    private String descKeywords; // Comma separated keywords for matching descriptions

}
