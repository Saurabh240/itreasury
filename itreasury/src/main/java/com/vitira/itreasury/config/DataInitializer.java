package com.vitira.itreasury.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vitira.itreasury.entity.Role;
import com.vitira.itreasury.entity.TransactionCategory;
import com.vitira.itreasury.repository.RoleRepository;
import com.vitira.itreasury.repository.TransactionCategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.util.List;

@Configuration
public class DataInitializer {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Bean
    public CommandLineRunner runner(RoleRepository roleRepository) {
        return args -> {
            if(roleRepository.findByName(Role.USER).isEmpty()) {
                roleRepository.save(Role.builder().name(Role.USER).build());
            }
        };
    }

    @Bean
    public CommandLineRunner preloadCategories(TransactionCategoryRepository categoryRepository) {
        return args -> {
            if (categoryRepository.count() == 0) { // Only preload if the table is empty
                // Load JSON file
                ClassPathResource resource = new ClassPathResource("default_transaction_categories.json");
                List<TransactionCategory> categories = objectMapper.readValue(resource.getInputStream(), new TypeReference<>() {});
                categoryRepository.saveAll(categories);
            }
        };
    }
}