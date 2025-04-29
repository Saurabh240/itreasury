package com.vitira.storage.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = "com.vitira.storage")
@Import({StorageConfig.class, AwsConfig.class, AzureConfig.class})
public class StorageAutoConfiguration {
} 