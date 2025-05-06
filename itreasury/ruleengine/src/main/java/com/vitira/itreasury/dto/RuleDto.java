package com.vitira.itreasury.dto;

import lombok.Data;

@Data
public class RuleDto {
    private Long id;
    private String name;
    private String conditionExpression;
    private boolean active;
    private int sequence;
}