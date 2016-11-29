package uk.co.keithsjohnson.lambda.api;

public interface SQSSender {
	String sendWithSQS(String uniqueId);
}
