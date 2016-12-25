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

package com.udoheld.aws.lambda.api;

/**
 * Custom request which handles the default Hello World test Template.
 *
 * @author Udo Held
 */
public class CustomRequest {
  private String key1;
  private String key2;
  private String key3;

  @Override
  public String toString() {
    return "CustomRequest{"
        + "key1='" + key1 + '\''
        + ", key2='" + key2 + '\''
        + ", key3='" + key3 + '\''
        + '}';
  }

  public String getKey1() {
    return key1;
  }

  public void setKey1(String key1) {
    this.key1 = key1;
  }

  public String getKey2() {
    return key2;
  }

  public void setKey2(String key2) {
    this.key2 = key2;
  }

  public String getKey3() {
    return key3;
  }

  public void setKey3(String key3) {
    this.key3 = key3;
  }
}
