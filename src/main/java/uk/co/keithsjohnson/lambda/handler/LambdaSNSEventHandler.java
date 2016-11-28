package uk.co.keithsjohnson.lambda.handler;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.SNSEvent;
import com.amazonaws.services.lambda.runtime.events.SNSEvent.MessageAttribute;
import com.amazonaws.services.lambda.runtime.events.SNSEvent.SNS;
import com.amazonaws.services.lambda.runtime.events.SNSEvent.SNSRecord;

import uk.co.keithsjohnson.lambda.LambdaSpringBootInvocation;
import uk.co.keithsjohnson.lambda.api.SQSSender;

public class LambdaSNSEventHandler {

	private static final String DD_MMM_YYYY_HH_MM_SS_S = "dd MMM yyyy HH:mm:ss.S";

	private static final String EUROPE_LONDON_TIMEZONE = "Europe/London";

	private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DD_MMM_YYYY_HH_MM_SS_S);

	private final LambdaSpringBootInvocation springBootInvocation;

	private SQSSender sqsSender;

	public LambdaSNSEventHandler() {
		springBootInvocation = new LambdaSpringBootInvocation();
		springBootInvocation.run();
		sqsSender = springBootInvocation.getApplicationContext().getBean(SQSSender.class);
	}

	public String handleSNSEvent(SNSEvent snsEvent, Context context) {

		context.getLogger().log("-----------------------------------");
		context.getLogger().log("START: " + getNowAsFormatedUKDateTimeString());

		List<SNSRecord> snsRecords = snsEvent.getRecords();
		context.getLogger().log("snsRecords.size()=" + snsRecords.size());
		for (SNSRecord snsRecord : snsRecords) {
			SNS snsMessage = snsRecord.getSNS();
			context.getLogger().log("snsMessage=" + snsMessage.getMessage());

			try {
				context.getLogger().log(snsMessage.getMessage());
				Map<String, MessageAttribute> messageAttributes = snsMessage.getMessageAttributes();
				String uniqueId = messageAttributes.get("uniqueId").getValue();
				context.getLogger().log(uniqueId);
				sqsSender.sendWithSQS(uniqueId);
				context.getLogger().log("-----------------------------------");
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		context.getLogger().log("END: " + getNowAsFormatedUKDateTimeString());
		return "OK";
	}

	protected String getNowAsFormatedUKDateTimeString() {
		return LocalDateTime.now(ZoneId.of(EUROPE_LONDON_TIMEZONE)).format(formatter);
	}
}
