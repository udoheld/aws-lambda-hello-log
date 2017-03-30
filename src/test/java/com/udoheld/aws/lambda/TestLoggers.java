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
import com.udoheld.aws.lambda.api.CustomRequest;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Tests mainly running through without errors only.
 */
public class TestLoggers {

  private Context context;

  private Logger log = Logger.getLogger(this.getClass().getName());

  @Before
  public void init(){
    context = initContext(log);
  }

  @Test
  public void testFullLogger(){
    HelloLogFull full = new HelloLogFull();
    full.handleRequest(new HashMap<String, Object>() {},context);
  }

  @Test
  public void testPojoLogger() {
    CustomRequest pojo = new CustomRequest();
    pojo.setKey1("val1");
    pojo.setKey2("val2");
    pojo.setKey3("val3");

    HelloLogPojo pojoLogger = new HelloLogPojo();
    pojoLogger.handleRequest(pojo, context);
  }

  @Test
  public void testSimpleLogger() {
    HelloLogSimple simple = new HelloLogSimple();
    simple.handleRequest(new HashMap<String, Object>() {},context);
  }

  @Test
  public void testStreamLogger() {
    String input = "{ \"key1\":\"val1\"}";
    ByteArrayInputStream bais = new ByteArrayInputStream(input.getBytes());
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    HelloLogStream stream = new HelloLogStream();

    stream.handleRequest(bais,baos,context);
  }

  @Test
  public void testStringNotWorkingLogger() {
    HelloLogStringDocNotWorking stringLogger = new HelloLogStringDocNotWorking();
    stringLogger.myHandler("my test string",context);
  }


  /**
   * Provides a logger.
   * @param log
   * @return
   */
  private LambdaLogger initLambdaLogger(Logger log){
    LambdaLogger logger = new LambdaLogger() {
      @Override
      public void log(String msg) {
        log.info(msg);
      }
    };

    return logger;
  }

  /**
   * Implements a dummy context providing a logger.
   * @param log
   * @return
   */
  public Context initContext(Logger log){
    Context context = new Context() {
      @Override
      public String getAwsRequestId() {
        return null;
      }

      @Override
      public String getLogGroupName() {
        return null;
      }

      @Override
      public String getLogStreamName() {
        return null;
      }

      @Override
      public String getFunctionName() {
        return null;
      }

      @Override
      public String getFunctionVersion() {
        return null;
      }

      @Override
      public String getInvokedFunctionArn() {
        return null;
      }

      @Override
      public CognitoIdentity getIdentity() {
        return new CognitoIdentity() {
          @Override
          public String getIdentityId() {
            return null;
          }

          @Override
          public String getIdentityPoolId() {
            return null;
          }
        };
      }

      @Override
      public ClientContext getClientContext() {
        return initClientContext();
      }

      @Override
      public int getRemainingTimeInMillis() {
        return 0;
      }

      @Override
      public int getMemoryLimitInMB() {
        return 0;
      }

      @Override
      public LambdaLogger getLogger() {
        return initLambdaLogger(log);
      }
    };
    return context;
  }

  private ClientContext initClientContext() {
    ClientContext clientContext = new ClientContext() {
      @Override
      public Client getClient() {
        return initClient();
      }

      @Override
      public Map<String, String> getCustom() {
        return null;
      }

      @Override
      public Map<String, String> getEnvironment() {
        return null;
      }
    };
    return clientContext;
  }

  private Client initClient() {
    Client client = new Client() {
      @Override
      public String getInstallationId() {
        return null;
      }

      @Override
      public String getAppTitle() {
        return null;
      }

      @Override
      public String getAppVersionName() {
        return null;
      }

      @Override
      public String getAppVersionCode() {
        return null;
      }

      @Override
      public String getAppPackageName() {
        return null;
      }

    };
    return client;
  }

}
