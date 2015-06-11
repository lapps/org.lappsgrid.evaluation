/*-
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

public class EvalPair<T> {
  T predicted;

  T reference;

  public EvalPair(T predicted, T reference) {
    super();
    this.predicted = predicted;
    this.reference = reference;
  }

  public T getPredicted() {
    return predicted;
  }

  public T getReference() {
    return reference;
  }

  public void setPredicted(T predicted) {
    this.predicted = predicted;
  }

  public void setReference(T reference) {
    this.reference = reference;
  }
}
