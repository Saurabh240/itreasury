package com.vitira.itreasury.rules;

import com.vitira.itreasury.dto.PaymentRecord;

public interface Rule {
    /**
     * Inspect and (if matched) mutate the record’s category/dueDate.
     * @return true if this rule “handled” the record (no further rules needed)
     */
    boolean apply(PaymentRecord record);
}

