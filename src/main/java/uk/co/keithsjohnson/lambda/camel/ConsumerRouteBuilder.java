package uk.co.keithsjohnson.lambda.camel;

//@Component
public class ConsumerRouteBuilder /* extends RouteBuilder */ {

	// @Override
	// public void configure() throws Exception {
	// System.out.println("Start ConsumerRouteBuilder.configure");
	// from("aws-sqs://demo?amazonSQSClient=#sqsClient&deleteIfFiltered=false")
	// .setHeader("identity")
	// .jsonpath("$['type']")
	// .filter(simple("${header.identity} == 'login'"))
	// .log("We have a message! ${header.logId} ${body}")
	// //
	// //
	// .to("file:target/output?fileName=login-message-${date:now:MMDDyy-HHmmss}.json")
	// ;
	// System.out.println("End ConsumerRouteBuilder.configure");
	// }
}