package org.melato.bus.plan;

import java.io.Serializable;

/** Represents the endpoints parameters in space and time for a plan.
 * Any of the parameters may be missing. 
 * <ul>
 * <li>Origin
 * <li>Destination
 * <li>Time
 * <li>Arrive At
 * @author alex
 *
 */
public class PlanEndpoints implements Serializable {
  private static final long serialVersionUID = 1L;
  public NamedPoint origin;
  public NamedPoint destination;
  /** minutes from midnight */
  public Integer time; 
  public boolean arriveAt;
  
  public String getName() {
    if ( origin == null && destination == null) {
      return "*";
    }
    if ( origin == null ) {
      return destination.toString();
    }
    if ( destination == null) {
      return origin.toString();
    }
    return origin + " -> " + destination;
  }

  @Override
  public String toString() {
    return "Endpoints: " + origin + " -> " + destination + " time=" + time + " arriveAt=" + arriveAt; 
  }
  
}
