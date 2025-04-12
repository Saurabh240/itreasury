package com.vitira.itreasury.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vitira.itreasury.entity.Bank;
import com.vitira.itreasury.entity.BankAccount;
import com.vitira.itreasury.repository.BankRepository;
import com.vitira.itreasury.repository.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class BankService {

    @Autowired
    private BankRepository bankRepository;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private List<Map<String, String>> swiftCodes;

    public BankService() {
        try {
            ClassPathResource resource = new ClassPathResource("swift_codes.json");
            swiftCodes = objectMapper.readValue(resource.getInputStream(), List.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load swift codes", e);
        }
    }

    @Transactional
    public Bank getOrCreateBank(String swiftCode) {
        // Check if the bank exists
        Bank bank = bankRepository.findBySwiftCode(swiftCode);
        if (bank == null) {
            // Find bank details from JSON
            Optional<Map<String, String>> bankDetails = swiftCodes.stream()
                .filter(b -> b.get("swift_code").equals(swiftCode))
                .findFirst();

            if (bankDetails.isPresent()) {
                Map<String, String> details = bankDetails.get();
                bank = new Bank();
                bank.setSwiftCode(swiftCode);
                bank.setName(details.get("bank_name"));
                bank.setBranch(details.get("branch"));
                bank.setAddress(details.get("city"));
                bankRepository.save(bank);
            } else {
                // If bank not found in JSON, create with minimal details
                bank = new Bank();
                bank.setSwiftCode(swiftCode);
                bank.setName("UNKNOWN BANK");
                bankRepository.save(bank);
            }
        }

        return bank;
    }
}