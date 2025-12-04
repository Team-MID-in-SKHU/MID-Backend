package com.skhu.mid_skhu.global.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("localstack")
public class LocalstackS3Config {

    @Value("${cloud.aws.s3.endpoint}")
    private String endpoint;

    @Value("${cloud.aws.region.static}")
    private String region;

    @Value("${cloud.aws.s3.path-style-access-enabled}")
    private Boolean pathStyle;

    @Value("${cloud.aws.credentials.access-key}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secret-key}")
    private String secretKey;

    @Bean
    @Primary
    public AmazonS3 amazonS3LocalStack() {
        return AmazonS3ClientBuilder
                .standard()
                .withEndpointConfiguration(
                        new EndpointConfiguration(endpoint, region)
                )
                .withPathStyleAccessEnabled(pathStyle)
                .withCredentials(
                        new AWSStaticCredentialsProvider(
                                new BasicAWSCredentials(accessKey, secretKey)
                        )
                )
                .build();
    }
}
