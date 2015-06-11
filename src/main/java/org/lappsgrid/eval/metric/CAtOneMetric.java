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
