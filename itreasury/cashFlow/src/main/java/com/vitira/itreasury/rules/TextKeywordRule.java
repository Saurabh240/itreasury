package com.vitira.itreasury.rules;

import com.vitira.itreasury.dto.PaymentCategory;
import com.vitira.itreasury.dto.PaymentRecord;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TextKeywordRule implements Rule {
    @Override
    public boolean apply(PaymentRecord r) {
        String txt = Optional.ofNullable(r.getText()).orElse("").toString().toLowerCase();
        if (txt.contains("tax")) {
            r.setCategory(PaymentCategory.TAXES);
            return true;
        }
        if (txt.contains("bank charge")) {
            r.setCategory(PaymentCategory.FINANCE_COST);
            return true;
        }
        return false;
    }
}