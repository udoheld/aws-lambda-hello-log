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

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;


/**
 * This class prints out basic information from the AWS Lambda function.
 *
 * @author Udo Held
 */
public class HelloLogSimple implements RequestHandler<Map<String,Object>,String> {

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
    sb.append("Input:").append(input).append(System.lineSeparator());
    sb.append("Context: ").append(HelloLogFull.contextToString(context))
        .append(System.lineSeparator());
    sb.append("Reduced-Environment: ").append(reduceEnvironmentToString());

    LambdaLogger logger = context.getLogger();
    logger.log(sb.toString());
    return "HelloLogSimple";
  }

  /**
   * Removes most of the known environment variables, but should keep your own settings from
   * the AWS Management Console.
   * @return The reduced environment as a String.
   */
  private String reduceEnvironmentToString() {
    StringBuilder sb = new StringBuilder();

    // Keeping AWS_REGION and AWS_DEFAULT_REGION
    String [] ignoreVariables = new String[]{ "PATH","LAMBDA_TASK_ROOT",
        "AWS_LAMBDA_FUNCTION_MEMORY_SIZE","AWS_SECRET_ACCESS_KEY","AWS_LAMBDA_LOG_GROUP_NAME",
        "XFILESEARCHPATH","AWS_LAMBDA_LOG_STREAM_NAME","LANG","LAMBDA_RUNTIME",
        "LAMBDA_RUNTIME_DIR","AWS_SESSION_TOKEN", "AWS_ACCESS_KEY_ID","AWS_ACCESS_KEY",
        "LD_LIBRARY_PATH","NLSPATH","AWS_SECRET_KEY",
        "AWS_LAMBDA_FUNCTION_VERSION","AWS_LAMBDA_FUNCTION_NAME",
    };
    List<String> ignoreList = Arrays.asList(ignoreVariables);

    Map<String,String> environment = System.getenv();
    environment.entrySet()
        .stream()
        .filter(entry -> !ignoreList.contains(entry.getKey()))
        .forEach(entry -> {
          sb.append("{").append(entry.getKey()).append("=").append(entry.getValue()).append("},");
        });

    return sb.toString();
  }

}
