package com.vitira.itreasury.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vitira.itreasury.entity.Bank;
import com.vitira.itreasury.entity.BankSwiftCode;
import com.vitira.itreasury.repository.BankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class BankService {

    @Autowired
    private BankRepository bankRepository;

    private final List<BankSwiftCode> swiftCodes;

    public BankService() {
        try {
            ClassPathResource resource = new ClassPathResource("swift_codes.json");
            ObjectMapper objectMapper = new ObjectMapper();
            swiftCodes = objectMapper.readValue(resource.getInputStream(), new TypeReference<>() {});
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
            Optional<BankSwiftCode> bankDetails = swiftCodes.stream()
                .filter(b -> b.getSwiftCode().equals(swiftCode))
                .findFirst();

            if (bankDetails.isPresent()) {
                BankSwiftCode bankSwiftCode = bankDetails.get();
                bank = new Bank();
                bank.setSwiftCode(swiftCode);
                bank.setName(bankSwiftCode.getBankName());
                bank.setBranch(bankSwiftCode.getBranch());
                bank.setAddress(bankSwiftCode.getCity());
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