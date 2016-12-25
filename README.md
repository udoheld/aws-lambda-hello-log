## AWS Lambda Java Hello Logs
This library can be used for 'Hello World' Lambda testing and demonstrates the
different possibilities of passing parameters to your Java Lambda function.
These classes are a good tool for getting started with implementing custom
Lambda functions.

The .jar must be built and uploaded to AWS using the AWS Console.

## Getting started and deploying the classes.

AWS Console -> Services -> Compute -> Lambda -> Create a Lambda function
-> Blank Function (template) -> Next -> Name: aws-hello-log -> Runtime: Java 8
-> Function package: Upload your .jar -> Environment variables: set some so
that you can see them in the output -> Handler: Pick one of the handlers e.g.
com.udoheld.aws.lambda.HelloLogSimple -> Role: Create new role from template(s)
-> Role name: Pick something e.g. aws-lambda-hello-role -> Memory (MB): 128
-> Timeout: 3s -> Next -> Create function -> Test -> Template: Leave the Hello
World template or modify it as you with -> Save And Test

## Loggers
The parameters can be passed in different ways to the logger. All working
loggers print their own name, the version and the input.
One logger which is not implemented and documented online lists retrieving an
integer as an example.

For getting started running HelloLogSimple is recommended.

None of the loggers makes use of the output. I didn't see anything AWS would
do with it. Maybe it could be used for local testing.

### HelloLogSimple
This logger prints the input and most of the useful variables. The would be a
starting point for a flexible implementation.

### HelloLogFull
This logger prints the input and all available variables.

### HelloLogPojo
This logger directly processes your input as a POJO. Therefor the parameter
names of your JSON have to match the bean properties of your implementation.
This implementation relies on the "Hello World" test template passing
<pre>{"key3":"value3","key2"="value2","key1":"value1"}</pre>.
This would be a starting point for a fully typed implementation.

### HelloLogStream
This logger provides the input as a stream. This could probably parse non-Json
input.

### HelloLogStringHandlerNotWorking
While this logger implements the handler and expects a String you will get an
JsonMappingException.

### HelloLogStringNotWorking
While this handler is implemented as per documentation it won't work. The AWS
runtime expects that a handler is implemented and will throw an exception.

## Known problems
At the time of writing the clientContext isn't populated. According to the
java-doc these should hold some client information as well as the self defined
environment variables. At the moment these variables can however be retrieved
getting the system variables as shown in the code.

In the [official documentation](
http://docs.aws.amazon.com/lambda/latest/dg/java-programming-model-req-resp.html)
a String implementation is documented. I didn't get it to work.



## License
AWS Lambda Java Hello Logs are released under version 3.0 of the
[LGPL License](https://www.gnu.org/licenses/lgpl-3.0.en.html).