package uk.co.keithsjohnson.lambda.camel;

import java.util.Random;

//@Component
public class ProducerRouteBuilder /* extends RouteBuilder */ {

	Random randomGenerator = new Random();
	private static final int MAX = 10;

	public static final String LOGIN_MESSAGE = "{" +
			"\"type\": \"login\"," +
			"\"payload\" : {" +
			"\"name\": \"ceposta\", " +
			"\"city\": \"phoenix\"" +
			"}" +
			"}";

	public static final String LOGIN_MESSAGE2 = "{" +
			"\"type\": \"login\"," +
			"\"payload\" : {" +
			"\"name\": \"Keith\", " +
			"\"city\": \"Stoke\"" +
			"}" +
			"}";

	private static final String LOGOUT_MESSAGE = "{" +
			"\"type\": \"logout\"," +
			"\"payload\" : {" +
			"\"name\": \"ceposta\", " +
			"\"city\": \"phoenix\"" +
			"}" +
			"}";

	// @Override
	// public void configure() throws Exception {
	// System.out.println("Start ProducerRouteBuilder.configure");
	// // every two seconds, send a message to the "demo" queue in SQS
	// // from("timer:kickoff?period=2000")
	// from("direct:start")
	// // .setHeader("", "")
	// .setBody()
	// .method(this, "generateJsonString")
	// .to("aws-sqs://demo?amazonSQSClient=#sqsClient&defaultVisibilityTimeout=2");
	// System.out.println("End ProducerRouteBuilder.configure");

	// }

	public Object generateJsonString() {
		// int rand = randomGenerator.nextInt(MAX);
		// if ((rand % 2) == 0) {
		return LOGIN_MESSAGE;
		// } else {
		// return LOGOUT_MESSAGE;
		// }
	}
}