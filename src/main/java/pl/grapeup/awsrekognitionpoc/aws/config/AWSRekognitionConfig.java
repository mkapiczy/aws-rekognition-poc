package pl.grapeup.awsrekognitionpoc.aws.config;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AWSRekognitionConfig {
    @Value("${aws.access-key}")
    private String accessKey;

    @Value("${aws.secret-key}")
    private String secretKey;

    @Value("${aws_region}")
    private String region;

    @Bean
    public AmazonRekognition amazonRekognition() {
        ClientConfiguration clientConfig = new ClientConfiguration();
        clientConfig.setConnectionTimeout(30000);
        clientConfig.setRequestTimeout(60000);
        clientConfig.setProtocol(Protocol.HTTPS);

        BasicAWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);

        return AmazonRekognitionClientBuilder
                .standard()
                .withClientConfiguration(clientConfig)
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(region)
                .build();
    }
}
