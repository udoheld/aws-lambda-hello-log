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
import com.udoheld.aws.lambda.api.CustomRequest;
import com.udoheld.aws.lambda.api.CustomResponse;

import java.util.ResourceBundle;

/**
 * Converts a Json Object directly into a pre-defined java object.
 * This one is adjusted for the default Hello World template.
 *
 * @author Udo Held
 */
public class HelloLogPojo implements RequestHandler<CustomRequest,CustomResponse> {

  @Override
  public CustomResponse handleRequest(CustomRequest input, Context context) {
    String version = ResourceBundle.getBundle("aws-lambda-hello-log-version").getString("version");

    StringBuilder sb = new StringBuilder();
    sb.append("Hello from Lambda ").append(this.getClass().getName()).append(".")
        .append(System.lineSeparator());
    sb.append("Version: ").append(version).append(System.lineSeparator());
    sb.append("Input:").append(input).append(System.lineSeparator());

    LambdaLogger logger = context.getLogger();
    logger.log(sb.toString());
    return new CustomResponse() ;
  }
}
