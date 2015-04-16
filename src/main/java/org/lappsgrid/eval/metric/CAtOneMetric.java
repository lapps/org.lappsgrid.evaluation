package org.lappsgrid.eval.metric;

import java.util.Collection;

public class CAtOneMetric extends Metric<Collection<BinaryJudgement>, Double> {

  @Override
  public Double measure(Collection<BinaryJudgement> input) {

    int total = 0, correct = 0, notAnswered = 0;

    for (BinaryJudgement bj : input) {
      total++;
      switch (bj) {
        case RIGHT:
          correct++;
          break;
        case NOT_ANSWERED:
          notAnswered++;
          break;
        default:
          break;
      }
    }
    
    double cAtOne = 0;
    if (total >0){
      cAtOne = (((double) correct) / ((double) total) * notAnswered + (double) correct) / (double) total;
    }
    
    return cAtOne;
  }

}
