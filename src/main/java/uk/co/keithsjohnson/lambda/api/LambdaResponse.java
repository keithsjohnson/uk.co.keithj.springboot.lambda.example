package uk.co.keithsjohnson.lambda.api;

public class LambdaResponse {
	String greetings;

	public String getGreetings() {
		return greetings;
	}

	public void setGreetings(String greetings) {
		this.greetings = greetings;
	}

	public LambdaResponse(String greetings) {
		this.greetings = greetings;
	}

	public LambdaResponse() {
	}

}