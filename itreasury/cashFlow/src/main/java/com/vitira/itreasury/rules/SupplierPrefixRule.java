package com.vitira.itreasury.rules;

import com.vitira.itreasury.dto.PaymentCategory;
import com.vitira.itreasury.dto.PaymentRecord;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@ConfigurationProperties(prefix="excel.rules.supplier-prefix")
public class SupplierPrefixRule implements Rule {
    private Map<String, PaymentCategory> map = new HashMap<>();

    public void setMap(Map<String, PaymentCategory> map) {
       this.map=map;
    }

    @Override
    public boolean apply(PaymentRecord r) {
        map.put("D",PaymentCategory.LOCAL_PAYMENT);
        map.put("C",PaymentCategory.LOCAL_PAYMENT);
        map.put("MS",PaymentCategory.MSME_PAYMENT);

        String code = r.getSupplierCode();

        if (code == null) return false;

        for (Map.Entry<String,PaymentCategory> e : map.entrySet()) {
            String prefix = e.getKey();
            PaymentCategory cat = e.getValue();
            if (code.startsWith(prefix)) {
                r.setCategory(cat);
                if (cat == PaymentCategory.MSME_PAYMENT) {
                    r.setDueDate(r.getInvoiceDate().plusDays(30));
                }
                return true;
            }
        }

        return false;
    }
}