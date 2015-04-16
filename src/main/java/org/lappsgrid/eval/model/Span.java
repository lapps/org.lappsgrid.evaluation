package org.lappsgrid.eval.model;

import com.google.common.base.Objects;
import com.google.common.base.Objects.ToStringHelper;
import org.apache.uima.jcas.tcas.Annotation;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Span implements Comparable<Span> {

  public long start;

  public long end;

  public Span(Annotation annotation) {
    this.start = annotation.getBegin();
    this.end = annotation.getEnd();
  }

  // TODO type enforcement
  public Span(JSONObject jsonObject) {
    start = (long) jsonObject.get("start");
    end = (long) jsonObject.get("end");
  }


  //TODO to util
  public Span(org.lappsgrid.serialization.lif.Annotation annotation) {
    start = (long) annotation.getStart();
    end = (long) annotation.getEnd();
  }


  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (int) (start ^ (start >>> 32));
    result = prime * result + (int) (end ^ (end >>> 32));
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Span other = (Span) obj;
    if (start != other.start)
      return false;
    if (end != other.end)
      return false;
    return true;
  }

  @Override
  public String toString() {
    ToStringHelper helper = Objects.toStringHelper(this);
    helper.add("begin", this.start);
    helper.add("end", this.end);
    return helper.toString();
  }

  @Override
  public int compareTo(Span o) {
    if (start < o.start) {
      return -1;
    } else if (start == o.start) {
      if (end > o.end) {
        return -1;
      } else if (end < o.end) {
        return 1;
      } else {
        return 0;
      }
    } else {
      return 1;
    }
  }

  //TODO move to lapps/ SpanEvaluation
  public static Map<Span, String> getSpanOutcomeMap(JSONArray annotations, String outcomeType) {
    Map<Span, String> spanToOutcome = new HashMap<Span, String>();
    if (annotations == null){
      return spanToOutcome;
    }
    Iterator<JSONObject> iter = annotations.iterator();
    while (iter.hasNext()) {
      JSONObject annotation = iter.next();
      Span span = new Span(annotation);
      JSONObject features = (JSONObject) annotation.get("features");
      //String word = (String) features.get("word");
      String featureString = "";
      if (features != null) {
        Object outcome = features.get(outcomeType);
        if (outcome != null) {
          featureString = (String) outcome;
        }
      }
      spanToOutcome.put(span, featureString);
    }
    return spanToOutcome;
  }



  
}
