package com.vitira.itreasury.enums;

import lombok.Getter;

@Getter
public enum Urgency {
    LOW ("LOW"),
    MEDIUM ("MED") ,
    HIGH ("HIGH");

    private final String values;

    Urgency(String values) {
        this.values = values;
    }
    public static Urgency fromString(String text) {
        for (Urgency urgency : Urgency.values()) {
            if (urgency.values.equalsIgnoreCase(text)) {
                return urgency;
            }
        }
        throw new IllegalArgumentException("No constant with text " + text + " found");
    }
}
