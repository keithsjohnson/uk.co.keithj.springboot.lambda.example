package example;

import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.lambda.runtime.Context;

public class Hello {
	public ResponseClass myHandler(RequestClass request, Context context) {
		String greetingString = String.format("Hello %s, %s.", request.firstName, request.lastName);
		context.getLogger().log(greetingString);

		SpringBootInvocation springBootInvocation = new SpringBootInvocation();
		springBootInvocation.run();
		greetingString = springBootInvocation.getApplicationContext().getBean(RequestComponent.class)
				.processRequest(greetingString, context);

		EnvironmentVariableCredentialsProvider environmentVariableCredentialsProvider = new EnvironmentVariableCredentialsProvider();

		AmazonDynamoDBClient amazonDynamoDBClient = new AmazonDynamoDBClient(environmentVariableCredentialsProvider);

		amazonDynamoDBClient.setRegion(Region.getRegion(Regions.fromName("eu-west-1")));

		DynamoDB dynamoDB = new DynamoDB(amazonDynamoDBClient);

		Table table = dynamoDB.getTable("Postcode");

		Item item = table.getItem("Postcode", "ST5 4EP");

		context.getLogger().log(item.getJSONPretty("Postcode"));

		return new ResponseClass(greetingString);
	}

	public static void main(String[] args) {
		SpringBootInvocation springBootInvocation = new SpringBootInvocation();
		springBootInvocation.run();
		springBootInvocation.getApplicationContext().getBean(RequestComponent.class).processRequest("KJ", null);
	}
}