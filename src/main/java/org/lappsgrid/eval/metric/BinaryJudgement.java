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
