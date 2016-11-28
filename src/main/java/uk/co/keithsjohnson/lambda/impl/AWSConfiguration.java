package uk.co.keithsjohnson.lambda.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;

@Configuration
public class AWSConfiguration {

	@Value("${use.profile.credentials:false}")
	private boolean useProfileCredentials = false;

	@Bean
	public AWSCredentialsProvider amazonAWSCredentialsProvider() {
		if (useProfileCredentials) {
			return new ProfileCredentialsProvider();
		} else {
			return new InstanceProfileCredentialsProvider(false);
		}
	}
}
