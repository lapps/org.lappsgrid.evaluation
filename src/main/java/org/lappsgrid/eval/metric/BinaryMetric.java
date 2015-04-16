package org.lappsgrid.eval.metric;

import com.google.common.base.Objects;


public class BinaryMetric<I> extends Metric<EvalPair<I>, BinaryJudgement> {

  @Override
  public BinaryJudgement measure(EvalPair<I> input) {
    if (input.predicted == null){
      return BinaryJudgement.NOT_ANSWERED;
    } else if (Objects.equal(input.predicted, input.reference)){
      return BinaryJudgement.RIGHT;
    } else {
      return BinaryJudgement.WRONG;
    }
  }

}
