package com.vitira.itreasury.rules;

import com.vitira.itreasury.dto.PaymentCategory;
import com.vitira.itreasury.dto.PaymentRecord;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RuleEngine {
    private final List<Rule> rules;

    public RuleEngine(List<Rule> rules) {
        this.rules = rules;
    }

    public void classify(PaymentRecord record) {
        for (Rule rule : rules) {
            if (rule.apply(record)) {
                return;
            }
        }
        record.setCategory(PaymentCategory.OTHER);
    }
}