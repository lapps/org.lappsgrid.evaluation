package org.lappsgrid.eval.metric;


abstract public class Metric<I, O>{

  abstract public O measure(I input);
  
  /**
   * Get the unique identifier of current component
   * 
   * @return component id
   */
  public String getComponentId() {
    return this.getClass().getSimpleName();
  }
  
}
