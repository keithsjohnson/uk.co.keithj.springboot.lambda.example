package uk.co.keithsjohnson.lambda.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.DeleteMessageRequest;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.MessageAttributeValue;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;

import uk.co.keithsjohnson.lambda.api.SQSReceiver;

@Component
public class SQSReceiverImpl implements SQSReceiver {

	private static Log log = LogFactory.getLog(SQSSenderImpl.class);

	@Autowired
	private AmazonSQSClient amazonSQSClient;

	@Override
	public void receiveMessageFromSQSWithUniqueId(String uniqueId) {
		ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest();
		receiveMessageRequest.setQueueUrl("https://sqs.eu-west-1.amazonaws.com/656423721434/demo");
		receiveMessageRequest.setAttributeNames(Arrays.asList("All"));
		int sdkRequestTimeout = 100;
		receiveMessageRequest.setSdkRequestTimeout(sdkRequestTimeout);
		receiveMessageRequest.withMessageAttributeNames("uniqueId");

		int maxRetries = 3;

		Optional<Message> optionalMessage = null;
		int tryCount = 1;
		do {
			log.info("START amazonSQSClient.receiveMessage tryCount=" + tryCount + " for uniqueId " + uniqueId
					+ " with sdkRequestTimeout=" + sdkRequestTimeout);
			ReceiveMessageResult receiveMessageResult = amazonSQSClient.receiveMessage(receiveMessageRequest);

			List<Message> messages = receiveMessageResult.getMessages();
			log.info("END amazonSQSClient.receiveMessage found " + messages.size() + " messages");

			optionalMessage = messages
					.stream()
					.filter(messageHasUniqueId(uniqueId))
					.findFirst();

			if (optionalMessage.isPresent()) {
				log.info("Found Message with UniqueId " + uniqueId + ": " + optionalMessage.get().getBody());

				DeleteMessageRequest deleteMessageRequest = new DeleteMessageRequest();
				deleteMessageRequest.setQueueUrl("https://sqs.eu-west-1.amazonaws.com/656423721434/demo");
				deleteMessageRequest.setReceiptHandle(optionalMessage.get().getReceiptHandle());

				amazonSQSClient.deleteMessage(deleteMessageRequest);
				log.info("amazonSQSClient.deleted Message");
			} else {
				log.info("NOT found Message with UniqueId " + uniqueId);
			}
			tryCount++;
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		} while (optionalMessage != null && !optionalMessage.isPresent() && tryCount <= maxRetries);
	}

	public static Predicate<Message> messageHasUniqueId(String uniqueId) {
		return message -> {
			Map<String, MessageAttributeValue> messageAttributes = message.getMessageAttributes();
			MessageAttributeValue messageAttributeValue = messageAttributes.get("uniqueId");
			if (messageAttributeValue == null) {
				return false;
			} else {
				return messageAttributeValue.getStringValue().equals(uniqueId);
			}
		};
	}
}
