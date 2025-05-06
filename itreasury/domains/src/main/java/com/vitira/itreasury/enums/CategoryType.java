package com.vitira.itreasury.enums;

import lombok.Getter;

@Getter
public enum CategoryType {
    INFLOW("INFLOW"),
    OUTFLOW("OUTFLOW");

    private final String type;

    CategoryType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static CategoryType fromString(String text) {
        for (CategoryType categoryType : CategoryType.values()) {
            if (categoryType.type.equalsIgnoreCase(text)) {
                return categoryType;
            }
        }
        throw new IllegalArgumentException("No constant with text " + text + " found");
    }
}
