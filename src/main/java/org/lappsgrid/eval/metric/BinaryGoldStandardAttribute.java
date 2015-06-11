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

import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;

/**
 * @author Diwang.
 */
public class BinaryGoldStandardAttribute {

  static public final String FeatureGSName = "Correct";

  static public String CORRECT = "y";

  static public String WRONG = "n";

  static BinaryGoldStandardAttribute gs_singleton = null;

  private Attribute gs_att;

  public BinaryGoldStandardAttribute() {
    FastVector gs_nominal_values = new FastVector(2);
    gs_nominal_values.addElement(WRONG);
    gs_nominal_values.addElement(CORRECT);
    gs_att = new Attribute(FeatureGSName, gs_nominal_values);
  }

  public static BinaryGoldStandardAttribute getSingleton() {
    if (gs_singleton == null) {
      gs_singleton = new BinaryGoldStandardAttribute();
    }
    return gs_singleton;
  }

  public boolean isCorrect(Instance instance) {
    return gs_att.value((int) instance.value(gs_att)).equals(CORRECT);
  }

  public String getLabel(Instance instance) {
    return gs_att.value((int) instance.value(gs_att));
  }

  public int indexOfCorrect() {
    return gs_att.indexOfValue(CORRECT);
  }

  public Attribute getAttribute() {
    return gs_att;
  }

}
