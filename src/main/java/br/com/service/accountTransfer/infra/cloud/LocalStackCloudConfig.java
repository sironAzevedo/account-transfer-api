package br.com.service.accountTransfer.infra.cloud;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LocalStackCloudConfig extends AbstractCloudConfig {

    @Value("${cloud.aws.endpoint.uri}")
    private String host;

    @Bean
    public AmazonSNS amazonSNSAsync() {
        return AmazonSNSClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(host, region))
                .withCredentials(getCredentialsProvider())
                .build();
    }
}
