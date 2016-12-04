package uk.co.keithsjohnson.lambda.handler;

import java.util.Properties;

import com.amazonaws.services.lambda.runtime.Context;

import uk.co.keithsjohnson.lambda.LambdaSpringBootInvocation;
import uk.co.keithsjohnson.lambda.api.LambdaRequest;
import uk.co.keithsjohnson.lambda.api.LambdaRequestProcessor;
import uk.co.keithsjohnson.lambda.api.LambdaResponse;
import uk.co.keithsjohnson.lambda.impl.LambdaRequestProcessorImpl;

public class LambdaRequestHandler {
	private final LambdaSpringBootInvocation springBootInvocation;

	private LambdaRequestProcessor requestComponentBean;

	public LambdaRequestHandler() {
		springBootInvocation = new LambdaSpringBootInvocation();
		springBootInvocation.run();
		requestComponentBean = springBootInvocation.getApplicationContext().getBean(LambdaRequestProcessorImpl.class);
	}

	public LambdaResponse requestHandler(LambdaRequest request, Context context) {
		String greetingString = String.format("Hello %s, %s.", request.getFirstName(), request.getLastName());
		context.getLogger().log("results" + greetingString);

		String results = requestComponentBean.processRequest(greetingString, context);
		context.getLogger().log("results: " + results);

		// getPostcode(context);

		// logProperties(context);

		// try {
		// Thread.sleep(2_000);
		// { catch (InterruptedException e) {
		// throw new RuntimeException(e);
		// }

		return new LambdaResponse(greetingString + ": " + results);
	}

	protected void logProperties(Context context) {
		Properties properties = System.getProperties();

		properties.keySet()
				.stream()
				.forEach(key -> context.getLogger().log(key + ": " + properties.getProperty((String) key) + "\r\n"));
	}

	public static void main(String[] args) {
		LambdaRequestHandler lambdaRequestHandler = new LambdaRequestHandler();
		lambdaRequestHandler.processRequest();
	}

	private void processRequest() {
		requestComponentBean.processRequest("KJ", null);
	}
}