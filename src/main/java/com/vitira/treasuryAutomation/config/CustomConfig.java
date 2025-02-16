package com.vitira.treasuryAutomation.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomConfig {

	/*
	 * Note: This class is added to create dummy bean for dependency injection in ODataConverter class
	 */
	
    @Bean
    public Class<?> myClass() {
        return String.class; // Replace MyClass with the actual class you want to inject
    }
}

