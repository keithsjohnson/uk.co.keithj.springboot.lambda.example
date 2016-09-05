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

Handler
-------
example.Hello::myHandler

Deployment File
---------------
build/distributions/uk.co.keithj.springboot.lambda.example-1.0.zip
