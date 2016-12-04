package uk.co.keithsjohnson.lambda.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.DynamodbEvent;

import uk.co.keithsjohnson.lambda.LambdaSpringBootInvocation;
import uk.co.keithsjohnson.lambda.api.DynamodbRecordStreamProcessor;

public class DynamodbLambdaRequestHandler {

	private final LambdaSpringBootInvocation springBootInvocation;

	private DynamodbRecordStreamProcessor dynamodbRecordStreamProcessor;

	public DynamodbLambdaRequestHandler() {
		springBootInvocation = new LambdaSpringBootInvocation();
		springBootInvocation.run();
		dynamodbRecordStreamProcessor = springBootInvocation.getApplicationContext()
				.getBean(DynamodbRecordStreamProcessor.class);
	}

	public String handleDynamodbEvent(DynamodbEvent dynamodbEvent, Context context) {

		context.getLogger().log("DynamodbLambdaRequestHandler:handleDynamodbEvent: " + dynamodbEvent.toString());

		dynamodbEvent.getRecords()
				.stream()
				.forEach(record -> dynamodbRecordStreamProcessor.processRecord(record, context));

		return "handleDynamodbEvent completed";
	}
}
