package uk.co.keithsjohnson.lambda.impl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sqs.AmazonSQSClient;

@Configuration
public class AwsClients {

	@Bean(name = "sqsClient")
	public AmazonSQSClient sqsClient() {
		AmazonSQSClient amazonSQSClient = new AmazonSQSClient();
		amazonSQSClient.setRegion(Region.getRegion(Regions.EU_WEST_1));
		return amazonSQSClient;
	}

	@Bean(name = "snsClient")
	public AmazonSNSClient snsClient() {
		AmazonSNSClient amazonSNSClient = new AmazonSNSClient();
		amazonSNSClient.setRegion(Region.getRegion(Regions.EU_WEST_1));
		return amazonSNSClient;
	}

}