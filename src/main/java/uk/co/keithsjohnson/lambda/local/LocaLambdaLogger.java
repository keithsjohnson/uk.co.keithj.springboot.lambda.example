package uk.co.keithsjohnson.lambda.local;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.amazonaws.services.lambda.runtime.LambdaLogger;

public class LocaLambdaLogger implements LambdaLogger {

	private static Log LOGGER = LogFactory.getLog(LocaLambdaLogger.class);

	public void log(String message) {
		LOGGER.info(message);
	}

}
