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
