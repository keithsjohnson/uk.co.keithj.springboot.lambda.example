package uk.co.keithsjohnson.lambda.api;

import java.util.Optional;

import com.amazonaws.services.lambda.runtime.Context;

public interface DynamoDBReader {
	Optional<String> getMessageWithId(String uniqueId, Context context);
}
