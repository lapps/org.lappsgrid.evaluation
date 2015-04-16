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
