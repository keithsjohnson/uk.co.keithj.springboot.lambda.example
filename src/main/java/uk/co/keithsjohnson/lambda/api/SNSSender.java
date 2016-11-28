package uk.co.keithsjohnson.lambda.api;

public interface SNSSender {
	void sendWithSNS(String uniqueId);
}
