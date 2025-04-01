package com.vitira.itreasury.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class TransactionCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

}
