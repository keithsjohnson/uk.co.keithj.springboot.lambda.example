package uk.co.keithsjohnson.lambda.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfiguration {
	@Value(value = "${configtest.value:default config value}")
	private String configValue;

	public String getConfigValue() {
		return configValue;
	}
}