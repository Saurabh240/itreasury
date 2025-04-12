package com.vitira.itreasury.service;
import com.vitira.itreasury.dto.BankwiseFundDTO;
import com.vitira.itreasury.dto.FundPositionDTO;
import com.vitira.itreasury.entity.Bank;
import com.vitira.itreasury.entity.BankAccount;
import com.vitira.itreasury.entity.Company;
import com.vitira.itreasury.repository.BankAccountRepository;
import com.vitira.itreasury.repository.BankRepository;
import com.vitira.itreasury.repository.CompanyRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FundPositionService {
    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private BankRepository bankRepository;

    public FundPositionDTO getFundPosition() {
        List<BankAccount> bankAccounts = bankAccountRepository.findAll();
        BigDecimal totalFunds = bankAccounts.stream()
                .map(BankAccount::getBalance)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Map<Long, BigDecimal> bankwiseFundsMap = bankAccounts.stream()
                .collect(Collectors.groupingBy(ba -> ba.getBank().getId(),
                        Collectors.mapping(BankAccount::getBalance,
                                Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))));

        List<BankwiseFundDTO> bankwiseFunds = bankwiseFundsMap.entrySet().stream()
                .map(entry -> {
                    Bank bank = bankRepository.findById(entry.getKey()).orElse(null);
                    return new BankwiseFundDTO(entry.getKey(), bank != null ? bank.getName() : null, entry.getValue());
                })
                .collect(Collectors.toList());

        String companyName = "Vitira"; // Default company name

        return FundPositionDTO.builder()
                .companyName(companyName)
                .totalFunds(totalFunds)
                .bankwiseFunds(bankwiseFunds)
                .build();
    }
}


