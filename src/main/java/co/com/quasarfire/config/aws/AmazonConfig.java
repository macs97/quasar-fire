package co.com.quasarfire.config.aws;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.AWSLambdaClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class AmazonConfig {

    public AWSCredentials awsCredentials() {
        return new DefaultAWSCredentialsProviderChain().getCredentials();
    }

    @Bean
    public AWSLambda config() {
        return AWSLambdaClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(awsCredentials()))
            .withRegion(Regions.US_EAST_1).build();
    }
}

