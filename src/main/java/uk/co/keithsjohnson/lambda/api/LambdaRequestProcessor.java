package uk.co.keithsjohnson.lambda.api;

import com.amazonaws.services.lambda.runtime.Context;

public interface LambdaRequestProcessor {
	String processRequest(String name, Context context);
}
