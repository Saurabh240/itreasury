package com.vitira.itreasury;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class TreasuryAutomationApplication {

	public static void main(String[] args) {
		SpringApplication.run(TreasuryAutomationApplication.class, args);
	}

}
