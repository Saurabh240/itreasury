package com.vitira.itreasury;

import com.vitira.itreasury.entity.Role;
import com.vitira.itreasury.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class TreasuryAutomationApplication {

	public static void main(String[] args) {
		SpringApplication.run(TreasuryAutomationApplication.class, args);
	}

	@Bean
	public CommandLineRunner runner(RoleRepository roleRepository) {
		return args -> {
			if(roleRepository.findByName(Role.USER).isEmpty()) {
				roleRepository.save(Role.builder().name(Role.USER).build());
			}
		};
	}

}
