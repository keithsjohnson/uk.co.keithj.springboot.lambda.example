package uk.co.keithsjohnson.lambda.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.MessageAttributeValue;
import com.amazonaws.services.sns.model.PublishRequest;

import uk.co.keithsjohnson.lambda.api.SNSSender;

@Component
public class SNSSenderImpl implements SNSSender {
	private static Log log = LogFactory.getLog(SNSSenderImpl.class);

	@Autowired
	private AmazonSNSClient amazonSNSClient;

	@Override
	public void sendWithSNS(String uniqueId) {
		log.info("amazonSNSClient sendMessage");

		MessageAttributeValue messageAttributeValue = new MessageAttributeValue();
		messageAttributeValue.setStringValue(uniqueId);
		messageAttributeValue.setDataType("String");

		Map<String, MessageAttributeValue> messageAttributes = new HashMap<>();
		messageAttributes.put("uniqueId", messageAttributeValue);

		PublishRequest publishRequest = new PublishRequest();
		publishRequest.setTopicArn("arn:aws:sns:eu-west-1:656423721434:snsDemo");
		publishRequest.setMessage("Hi SNS Demo with uniqueId: " + uniqueId);
		publishRequest.setMessageAttributes(messageAttributes);

		amazonSNSClient.publish(publishRequest);

		log.info("amazonSNSClient sendMessage DONE");
	}
}
