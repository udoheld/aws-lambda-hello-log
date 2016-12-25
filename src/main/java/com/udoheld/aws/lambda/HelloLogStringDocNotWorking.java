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

import java.util.ResourceBundle;

/**
 * This implementation is listed on the
 * homepage (http://docs.aws.amazon.com/lambda/latest/dg/java-programming-model-req-resp.html), but
 * isn't working. You will get an error
 * <pre>"errorMessage": "Class does not implement an appropriate handler interface
 com.udoheld.aws.lambda.HelloLogString"</pre>
 *
 * @author Udo Held
 */
public class HelloLogStringDocNotWorking {

  /**
   * Handler for requests as per AWS documentation not implementing an interface etc.
   * @param myRequest input parameter
   * @param context context parameter
   * @return output values
   */
  public String myHandler(String myRequest, Context context) {

    String version = ResourceBundle.getBundle("aws-lambda-hello-log-version").getString("version");

    StringBuilder sb = new StringBuilder();
    sb.append("Hello from Lambda ").append(this.getClass().getName()).append(".")
        .append(System.lineSeparator());
    sb.append("Version: ").append(version).append(System.lineSeparator());
    sb.append("Input:").append(myRequest).append(System.lineSeparator());

    LambdaLogger logger = context.getLogger();
    logger.log(sb.toString());
    return "HelloLogString" ;
  }
}