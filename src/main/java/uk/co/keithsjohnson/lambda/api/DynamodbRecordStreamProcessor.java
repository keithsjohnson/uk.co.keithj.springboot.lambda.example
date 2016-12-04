package uk.co.keithsjohnson.lambda.api;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.DynamodbEvent.DynamodbStreamRecord;

public interface DynamodbRecordStreamProcessor {
	void processRecord(DynamodbStreamRecord dynamodbStreamRecord, Context context);
}
