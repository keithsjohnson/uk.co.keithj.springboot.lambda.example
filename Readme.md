Project
-------
Test Spring Boot AWS Lambda Example

Build
-----
gradlew clean build

Requirements
------------
DynamoDB table called Postcode
Item: Postcode Value: ST5 4EP

Test Data
---------
{ "firstName":"Harry", "lastName":"Potter" }

Links
-----
http://docs.aws.amazon.com/lambda/latest/dg/java-handler-io-type-pojo.html
https://github.com/rob-baily/aws-lambda-spring

Handler
-------
example.Hello::myHandler

Deployment File
---------------
build/distributions/uk.co.keithj.springboot.lambda.example-1.0.zip


uk.co.keithsjohnson.lambda.handler.LambdaRequestHandler::requestHandler
uk.co.keithsjohnson.lambda.handler.LambdaSNSEventHandler::handleSNSEvent
uk.co.keithsjohnson.lambda.handler.DynamodbLambdaRequestHandler::handleDynamodbEvent

Description
-----------
The process is as follows:
1. The LambdaRequestHandler receives a request to process message with a uniqueId.
2. The LambdaRequestHandler passes the request to SNS Demo Topic with the uniqueId.
3. The LambdaRequestHandler waits periodically receiving messages from SQS demo Queue and looking for message with correct uniqueId message attribute.
4. The LambdaSNSEventHandler receives message from SNS with the uniqueId.
5. The LambdaSNSEventHandle sends message to SQS demo Queue with the uniqueId.
6. The LambdaRequestHandler deletes the message from SQS demo Queue if found with the correct uniqueId message attribute is received within Timeout.
7. The LambdaRequestHandler returns response to Lambda request.
