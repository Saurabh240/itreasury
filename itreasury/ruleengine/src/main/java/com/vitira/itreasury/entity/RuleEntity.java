package com.vitira.itreasury.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "rules")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RuleEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, length = 1024)
    private String conditionExpression;

    @Column(nullable = false, length = 1024)
    private String actionExpression;

    @Column(nullable = false)
    private boolean active;

    @Column(nullable = false)
    private int sequence;
}