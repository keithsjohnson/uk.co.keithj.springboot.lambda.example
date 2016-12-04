package uk.co.keithsjohnson.lambda.api;

import com.amazonaws.services.lambda.runtime.Context;

public interface DynamoDBWriter {
	String getPostcode(String postCode, Context context);

	String writeMessageToQueueTable(String uniqueId, String message, Context context);

	String writeMessageToResponseQueueTable(String uniqueId, String message, Context context);

	void deleteMessageFromQueueTable(String uniqueId, Context context);

	void deleteMessageFromResponseQueueTable(String uniqueId, Context context);
}
