package uk.co.keithsjohnson.lambda.impl;

import java.util.Optional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.lambda.runtime.Context;

import uk.co.keithsjohnson.lambda.api.DynamoDBReader;

@Component
public class DynamoDBReaderImpl implements DynamoDBReader {
	private static Log LOG = LogFactory.getLog(DynamoDBReaderImpl.class);
	private final DynamoDB dynamoDB;

	public DynamoDBReaderImpl() {
		EnvironmentVariableCredentialsProvider environmentVariableCredentialsProvider = new EnvironmentVariableCredentialsProvider();

		AmazonDynamoDBClient amazonDynamoDBClient = new AmazonDynamoDBClient(environmentVariableCredentialsProvider);

		amazonDynamoDBClient.setRegion(Region.getRegion(Regions.fromName("eu-west-1")));

		dynamoDB = new DynamoDB(amazonDynamoDBClient);
	}

	@Override
	public Optional<String> getMessageWithId(String uniqueId, Context context) {
		Table table = dynamoDB.getTable("ResponseQueue");

		int maxRetries = 7;

		int tryCount = 1;
		Item item = null;

		do {
			item = table.getItem("id", uniqueId);
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
			LOG.info("tryCount: " + tryCount + (item == null ? " not found yet. " : " found: " + item.toString()));
			tryCount++;
		} while (item == null && tryCount <= maxRetries);

		if (item == null) {
			LOG.info("NOT FOUND uniqueId " + uniqueId + " in ResponseQueue table");
			return Optional.ofNullable(null);
		} else {
			LOG.info(item.getString("message"));
			return Optional.ofNullable(item.getString("message"));
		}
	}
}
