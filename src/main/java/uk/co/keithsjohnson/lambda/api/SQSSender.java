package uk.co.keithsjohnson.lambda.api;

public interface SQSSender {
	void sendWithSQS(String uniqueId);
}
