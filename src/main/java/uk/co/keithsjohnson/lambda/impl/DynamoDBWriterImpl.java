package uk.co.keithsjohnson.lambda.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.KeyAttribute;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.PutItemResult;
import com.amazonaws.services.lambda.runtime.Context;

import uk.co.keithsjohnson.lambda.api.DynamoDBWriter;

@Component
public class DynamoDBWriterImpl implements DynamoDBWriter {

	private static Log LOG = LogFactory.getLog(DynamoDBWriterImpl.class);
	private final DynamoDB dynamoDB;

	public DynamoDBWriterImpl() {
		EnvironmentVariableCredentialsProvider environmentVariableCredentialsProvider = new EnvironmentVariableCredentialsProvider();

		AmazonDynamoDBClient amazonDynamoDBClient = new AmazonDynamoDBClient(environmentVariableCredentialsProvider);

		amazonDynamoDBClient.setRegion(Region.getRegion(Regions.fromName("eu-west-1")));

		dynamoDB = new DynamoDB(amazonDynamoDBClient);
	}

	@Override
	public String getPostcode(String postCode, Context context) {
		Table table = dynamoDB.getTable("Postcode");

		Item item = table.getItem("Postcode", postCode);

		context.getLogger().log(item.getJSONPretty("Postcode"));

		return item.getJSONPretty("Postcode");
	}

	@Override
	public String writeMessageToQueueTable(String uniqueId, String message, Context context) {
		String tableName = "Queue";
		return writeMessageToTable(uniqueId, message, context, tableName);
	}

	@Override
	public String writeMessageToResponseQueueTable(String uniqueId, String message, Context context) {
		String tableName = "ResponseQueue";
		return writeMessageToTable(uniqueId, message, context, tableName);
	}

	@Override
	public void deleteMessageFromQueueTable(String uniqueId, Context context) {
		deleteRecordFromTable("Queue", uniqueId, context);
	}

	@Override
	public void deleteMessageFromResponseQueueTable(String uniqueId, Context context) {
		deleteRecordFromTable("ResponseQueue", uniqueId, context);
	}

	private String writeMessageToTable(String uniqueId, String message, Context context, String tableName) {
		LOG.info("get table " + tableName);
		Table table = dynamoDB.getTable(tableName);
		LOG.info("create new item");
		Item item = new Item()
				.withString("id", uniqueId)
				.withString("message", message);
		LOG.info("put new item");
		PutItemOutcome putItemOutcome = table.putItem(item);
		PutItemResult putItemResult = putItemOutcome.getPutItemResult();
		LOG.info("Written message to table " + tableName + " with uniqueId " + uniqueId + ", "
				+ putItemResult.toString());
		return putItemResult.toString();
	}

	private void deleteRecordFromTable(String tableName, String uniqueId, Context context) {
		Table table = dynamoDB.getTable(tableName);
		KeyAttribute keyAttribute = new KeyAttribute("id", uniqueId);
		table.deleteItem(keyAttribute);
	}
}