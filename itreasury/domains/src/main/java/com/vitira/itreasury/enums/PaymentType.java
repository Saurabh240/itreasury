package com.vitira.itreasury.enums;

public enum PaymentType {
    LOCAL_PAYMENT("LOCAL"),
    MSME_PAYMENT("MSME"),
    IMPORT_PAYMENT("IMPORT"),
    TAXES("TAXES"),
    FINANCE_COST("FINANCE"),
    SALARIES ("SALARIES"),
    EXPORT_PAYMENT ("EXPORT"),
    OTHERS ("OTHERS");

    private final String values;

    PaymentType(String type) {
        this.values = type;
    }

    public String getValues() {
        return values;
    }

    public static PaymentType fromString(String text) {
        for (PaymentType paymentType : PaymentType.values()) {
            if (paymentType.values.equalsIgnoreCase(text)) {
                return paymentType;
            }
        }
        throw new IllegalArgumentException("No constant with text " + text + " found");
    }
}