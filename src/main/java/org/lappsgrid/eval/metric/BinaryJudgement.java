/*
 * Copyright 2014 The Language Application Grid
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.lappsgrid.eval.metric;

public enum BinaryJudgement {

  RIGHT("lapps.org/type/judge/RIGHT", "Right"),
  WRONG("lapps.org/type/judge/WRONG", "Wrong"),
  NOT_ANSWERED("lapps.org/type/judge/NOT_ANSWERED", "Not Answered");

  String uri;

  String name;

  BinaryJudgement(String uri, String name) {
    this.uri = uri;
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  //TODO Replace with JSON-LD
  @Override
  public String toString() {
    return "\"" + uri + "\"";
  }

  public String getUri() {
    return uri;
  }

  public void setUri(String uri) {
    this.uri = uri;
  }

}
