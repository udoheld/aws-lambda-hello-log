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
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ResourceBundle;

/**
 * Processes the AWS Lambda input as a stream.
 *
 * @author Udo Held
 */
public class HelloLogStream implements RequestStreamHandler {
  @Override
  public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) {

    String version = ResourceBundle.getBundle("aws-lambda-hello-log-version").getString("version");

    String input = null;
    try {
      input = readInputStream(inputStream);
    } catch (IOException expected) {
    }

    StringBuilder sb = new StringBuilder();
    sb.append("Hello from Lambda ").append(this.getClass().getName()).append(".")
        .append(System.lineSeparator());
    sb.append("Version: ").append(version).append(System.lineSeparator());
    // Produced now useful output at the time of writing.
    sb.append("Input:").append(input).append(System.lineSeparator());

    LambdaLogger logger = context.getLogger();
    logger.log(sb.toString());
  }

  private String readInputStream(InputStream inputStream) throws IOException {
    StringBuilder sb = new StringBuilder();
    byte [] buf = new byte[128];

    int readBytes = 0;
    while ((readBytes = inputStream.read(buf)) != -1) {
      String bufStr = new String(buf,0,readBytes);
      sb.append(bufStr);
      if (readBytes < buf.length) {
        break;
      }
    }
    return sb.toString();
  }
}
