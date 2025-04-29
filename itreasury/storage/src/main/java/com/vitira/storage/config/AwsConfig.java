package com.vitira.storage.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "app.storage.type", havingValue = "aws")
public class AwsConfig {
    @Value("${aws.access.key.id:}")
    private String accessKeyId;

    @Value("${aws.access.key.secret:}")
    private String accessKeySecret;

    @Value("${aws.region:us-east-1}")
    private String region;

    @Bean
    public AmazonS3 amazonS3() {
        if (accessKeyId.isEmpty() || accessKeySecret.isEmpty()) {
            throw new IllegalStateException("AWS credentials are required when using AWS storage type");
        }
        
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKeyId, accessKeySecret);
        return AmazonS3ClientBuilder.standard()
                .withRegion(region)
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();
    }
} 