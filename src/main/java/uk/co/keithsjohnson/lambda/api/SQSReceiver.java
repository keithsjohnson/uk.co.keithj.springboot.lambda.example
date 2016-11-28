package uk.co.keithsjohnson.lambda.api;

public interface SQSReceiver {
	void receiveMessageFromSQSWithUniqueId(String uniqueId);
}
