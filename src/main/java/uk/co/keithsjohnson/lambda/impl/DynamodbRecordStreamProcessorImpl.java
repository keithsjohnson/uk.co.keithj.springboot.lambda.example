package uk.co.keithsjohnson.lambda.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.DynamodbEvent.DynamodbStreamRecord;

import uk.co.keithsjohnson.lambda.api.DynamoDBWriter;
import uk.co.keithsjohnson.lambda.api.DynamodbRecordStreamProcessor;

@Component
public class DynamodbRecordStreamProcessorImpl implements DynamodbRecordStreamProcessor {

	private static Log LOG = LogFactory.getLog(DynamoDBWriterImpl.class);
	@Autowired
	private DynamoDBWriter dynamoDBWriter;

	@Override
	public void processRecord(DynamodbStreamRecord dynamodbStreamRecord, Context context) {
		LOG.info(dynamodbStreamRecord.toString());

		if (dynamodbStreamRecord.getEventName().equals("INSERT")) {
			AttributeValue uniqueId = dynamodbStreamRecord.getDynamodb().getNewImage().get("id");
			AttributeValue message = dynamodbStreamRecord.getDynamodb().getNewImage().get("message");
			LOG.info("uniqueId: " + uniqueId.getS() + ", message: " + message.getS());

			dynamoDBWriter.writeMessageToResponseQueueTable(uniqueId.getS(), message.getS(), context);
			dynamoDBWriter.deleteMessageFromQueueTable(uniqueId.getS(), context);
		}
	}
}
