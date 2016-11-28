package uk.co.keithsjohnson.lambda.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.MessageAttributeValue;
import com.amazonaws.services.sqs.model.SendMessageRequest;

import uk.co.keithsjohnson.lambda.api.SQSSender;

@Component
public class SQSSenderImpl implements SQSSender {

	private static Log log = LogFactory.getLog(SQSSenderImpl.class);

	@Autowired
	private AmazonSQSClient amazonSQSClient;

	@Override
	public void sendWithSQS(String uniqueId) {
		log.info("amazonSQSClient sendMessage");
		SendMessageRequest sendMessageRequest = new SendMessageRequest();
		sendMessageRequest.setMessageBody("Hi Demo Queue with uniqueId: " + uniqueId);
		sendMessageRequest.setQueueUrl("https://sqs.eu-west-1.amazonaws.com/656423721434/demo");

		MessageAttributeValue messageAttributeValue = new MessageAttributeValue();
		messageAttributeValue.setStringValue(uniqueId);
		messageAttributeValue.setDataType("String");

		Map<String, MessageAttributeValue> messageAttributes = new HashMap<>();
		messageAttributes.put("uniqueId", messageAttributeValue);

		sendMessageRequest.setMessageAttributes(messageAttributes);

		amazonSQSClient.sendMessage(sendMessageRequest);

		log.info("amazonSQSClient sendMessage DONE");
	}

}
