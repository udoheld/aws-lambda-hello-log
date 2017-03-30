/*
    Copyright 2016 the original author or authors.

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published
    by the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this program. If not, see
    <http://www.gnu.org/licenses/>.
 */

package com.udoheld.aws.lambda;

import com.amazonaws.services.lambda.runtime.Client;
import com.amazonaws.services.lambda.runtime.ClientContext;
import com.amazonaws.services.lambda.runtime.CognitoIdentity;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * This class prints out all information passed to the AWS Lambda function and all information
 * from the Environment.
 *
 * @author Udo Held
 */
public class HelloLogFull implements RequestHandler<Map<String,Object>,String> {

  /**
   * This method is called by AWS.
   * @param input Json Object from AWS
   * @param context Context Object from AWS
   * @return output value
   */
  @Override
  public String handleRequest(Map<String, Object> input, Context context) {
    String version = ResourceBundle.getBundle("aws-lambda-hello-log-version").getString("version");

    StringBuilder sb = new StringBuilder();
    sb.append("Hello from Lambda ").append(this.getClass().getName()).append(".")
        .append(System.lineSeparator());
    sb.append("Version: ").append(version).append(System.lineSeparator());
    sb.append("Input:" ).append(input).append(System.lineSeparator());
    sb.append("Context: ").append(contextToString(context)).append(System.lineSeparator());
    sb.append("System: ").append(systemVariablesToString());

    LambdaLogger logger = context.getLogger();
    logger.log(sb.toString());
    return "HelloLogJson";
  }

  /**
   * Converts the context to a String.
   * @param context Context to be converted.
   * @return String containing the context parameters.
   */
  public static String contextToString(Context context) {
    StringBuilder sb = new StringBuilder();
    sb.append("{awsRequestId=").append(context.getAwsRequestId()).append("},");
    sb.append("{clientContext=")
        .append(clientContextToString(context.getClientContext())).append("},");
    sb.append("{functionName=").append(context.getFunctionName()).append("},");
    sb.append("{functionVersion=").append(context.getFunctionVersion()).append("},");
    sb.append("{identity=").append(identityToString(context.getIdentity())).append("},");
    sb.append("{invokedFunctionArn=").append(context.getInvokedFunctionArn()).append("},");
    sb.append("{logGroupName=").append(context.getLogGroupName()).append("},");
    sb.append("{logStreamName=").append(context.getLogStreamName()).append("},");
    sb.append("{memoryLimitMB=").append(context.getMemoryLimitInMB()).append("},");
    sb.append("{remainingTimeInMillis=").append(context.getRemainingTimeInMillis()).append("}");

    return sb.toString();
  }

  /**
   * Converts the system and environment variables to a String.
   * @return String containing all set environment variables and properties.
   */
  private String systemVariablesToString() {
    StringBuilder sb = new StringBuilder();

    // This section will print out the environment variables set in the console.
    sb.append("Environment: ");
    Map<String, String> environment = System.getenv();
    environment.forEach((key,value)-> {
      sb.append("{").append(key).append("=").append(value).append("},");
    });
    sb.append(System.lineSeparator());

    sb.append("Properties: ");
    Properties props = System.getProperties();

    props.forEach((key,value)-> {
      sb.append("{").append(key).append("=").append(value).append("},");
    });

    return sb.toString();
  }

  /**
   * Prints the client context which should contain the environment variables, but returns null
   * during testing at the time of writing.
   * @param clientContext ClientContext to be converted.
   * @return String containing the client context. "null" if empty
   */
  public static String clientContextToString(ClientContext clientContext) {
    if (clientContext == null) {
      return "null";
    }

    StringBuilder sb = new StringBuilder();
    if (clientContext.getClient() != null) {
      Client client = clientContext.getClient();
      sb.append("{appPackageName=").append(client.getAppPackageName()).append("},");
      sb.append("{appTitle=").append(client.getAppTitle()).append("},");
      sb.append("{appVersionCode=").append(client.getAppVersionCode()).append("},");
      sb.append("{installationId=").append(client.getInstallationId()).append("}");
    }

    sb.append("{custom=").append(clientContext.getCustom()).append("}");
    sb.append("{environment=").append(clientContext.getEnvironment()).append("}");

    return sb.toString();
  }

  /**
   * Prints out the identity information.
   * @param identity CognitoIdentity
   * @return String containing the cognito data. "null" if empty
   */

  public static String identityToString(CognitoIdentity identity) {
    if (identity == null) {
      return "null";
    }

    StringBuilder sb = new StringBuilder();
    sb.append("{identityId=").append(identity.getIdentityId()).append("}");
    sb.append("{identityPoolId=").append(identity.getIdentityPoolId()).append("]");
    return sb.toString();
  }
}
