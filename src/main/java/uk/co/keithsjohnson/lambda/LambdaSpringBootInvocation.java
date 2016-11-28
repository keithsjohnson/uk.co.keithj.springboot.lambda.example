package uk.co.keithsjohnson.lambda;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import uk.co.keithsjohnson.lambda.configuration.TestConfiguration;

@Configuration
@EnableAutoConfiguration
@ComponentScan()
public class LambdaSpringBootInvocation {
	private ApplicationContext applicationContext;

	public LambdaSpringBootInvocation() {
	}

	public void run() {
		String[] args = new String[0];
		applicationContext = SpringApplication.run(LambdaSpringBootInvocation.class, args);
		/*
		 * applicationContext = new SpringApplicationBuilder() .main(getClass())
		 * .bannerMode(Banner.Mode.OFF) .web(false) .sources(getClass())
		 * .addCommandLineProperties(false) .build() .run();
		 */
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	@Bean
	@Scope("prototype")
	public TestConfiguration testConfiguration() {
		return new TestConfiguration();
	}
}