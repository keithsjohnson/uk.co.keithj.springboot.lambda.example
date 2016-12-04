package uk.co.keithsjohnson.lambda.impl;

import java.util.Optional;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.amazonaws.services.lambda.runtime.Context;

import uk.co.keithsjohnson.lambda.api.DynamoDBReader;
import uk.co.keithsjohnson.lambda.api.DynamoDBWriter;
import uk.co.keithsjohnson.lambda.api.LambdaRequestProcessor;
import uk.co.keithsjohnson.lambda.api.SNSSender;
import uk.co.keithsjohnson.lambda.api.SQSReceiver;
import uk.co.keithsjohnson.lambda.configuration.TestConfiguration;

@Component
@Scope("prototype")
public class LambdaRequestProcessorImpl implements LambdaRequestProcessor {
	private static Log log = LogFactory.getLog(LambdaRequestProcessorImpl.class);

	@Autowired
	ApplicationContext applicationContext;

	@Autowired
	Environment environment;

	// @Autowired
	// CamelContext camelContext;

	@Value(value = "${configtest.value}")
	private String configValue;

	// @Autowired
	// private SQSSender sqsSender;

	@Autowired
	private SNSSender snsSender;

	@Autowired
	private SQSReceiver sqsReceiver;

	@Autowired
	private TestConfiguration testConfiguration;

	@Autowired
	private DynamoDBWriter dynamoDBWriter;

	@Autowired
	private DynamoDBReader dynamoDBReader;

	@Override
	public String processRequest(String name, Context context) {
		log.info("In the hello function");
		log.info("Test value here = " + configValue);
		log.info("env value = " + environment.getProperty("configtest.value"));
		log.info("Test configuration value = " + testConfiguration.getConfigValue());
		// useCamel(name);

		String uniqueId = UUID.randomUUID().toString();

		// sqsSender.sendWithSQS(uniqueId);

		// snsSender.sendWithSNS(uniqueId, "Hi SNS Demo with uniqueId: ");

		// String postCode = dynamoDBWriter.getPostcode("ST5 4EP", context);

		dynamoDBWriter.writeMessageToQueueTable(uniqueId, "First DynamoDB Message", context);

		Optional<String> optionalMessage = dynamoDBReader.getMessageWithId(uniqueId, context);

		String message = null;

		if (optionalMessage.isPresent()) {
			dynamoDBWriter.deleteMessageFromResponseQueueTable(uniqueId, context);
			message = optionalMessage.get();
		} else {
			message = "NOT FOUND";
		}

		// sqsReceiver.receiveMessageFromSQSWithUniqueId(uniqueId);

		return String.format("Hello %s. Message: %s", name, message);
	}

	protected void useCamel(String name) {
		// try {
		// camelContext.start();
		// } catch (Exception e) {
		// throw new RuntimeException(e);
		// }
		// log.info("ProducerTemplate");
		// ProducerTemplate template = camelContext.createProducerTemplate();
		// log.info("sendBodyAndHeader");

		// template.sendBodyAndHeader("direct:start",
		// ProducerRouteBuilder.LOGIN_MESSAGE2, "logId", "log-id-value");
		// template.setDefaultEndpointUri("direct:start");
		// try {
		// log.info("start");
		// template.start();
		// try {
		// Thread.sleep(2_000);
		// } catch (InterruptedException e) {
		// throw new RuntimeException(e);
		// }
		// log.info("stop");
		// template.stop();
		// } catch (Exception e) {
		// throw new RuntimeException(e);
		// }
		// log.info(String.format("Hello %s.", name));
	}
}