package example;

import org.springframework.beans.factory.annotation.Value;

public class TestConfiguration {
	@Value(value = "${configtest.value:default config value}")
	private String configValue;

	public String getConfigValue() {
		return configValue;
	}
}