package com.vitira.itreasury.enums;

public enum PaymentType {
    LOCAL_PAYMENT("LOCAL_PAYMENT"),
    MSME_PAYMENT("MSME_PAYMENT"),
    IMPORT_PAYMENT("IMPORT_PAYMENT"),
    TAXES("TAXES"),
    FINANCE_COST("FINANCE_COST"),
    SALARIES ("SALARIES"),
    OTHERS ("OTHERS");

    private final String type;

    PaymentType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static PaymentType fromString(String text) {
        for (PaymentType paymentType : PaymentType.values()) {
            if (paymentType.type.equalsIgnoreCase(text)) {
                return paymentType;
            }
        }
        throw new IllegalArgumentException("No constant with text " + text + " found");
    }
}