package com.vitira.itreasury.rules;

import com.vitira.itreasury.dto.PaymentCategory;
import com.vitira.itreasury.dto.PaymentRecord;
import org.springframework.stereotype.Component;

@Component
public class ImportPaymentRule implements Rule {
    @Override
    public boolean apply(PaymentRecord r) {
        if (!"INR".equalsIgnoreCase(r.getDocCurr())) {
            r.setCategory(PaymentCategory.IMPORT_PAYMENT);
            return true;
        }
        return false;
    }
}