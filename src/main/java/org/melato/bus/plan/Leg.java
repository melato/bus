package org.melato.bus.plan;

import java.io.Serializable;

import org.melato.bus.model.RStop;
import org.melato.bus.model.RouteId;
import org.melato.bus.model.Stop;

public class Leg implements Serializable {
  private static final long serialVersionUID = 1L;
  /** The index of the leg in the sequence.  Used for putting legs in order. */
  public int    index;
  public RouteId routeId;
  public Stop stop1;
  public Stop stop2;
  public Leg(RStop stop) {
    this.routeId = stop.getRouteId();
    this.stop1 = stop.getStop();      
  }
  
  public Leg(RouteId routeId, Stop stop1, Stop stop2) {
    super();
    this.routeId = routeId;
    this.stop1 = stop1;
    this.stop2 = stop2;
  }

  public RouteId getRouteId() {
    return routeId;
  }
  public Stop getStop1() {
    return stop1;
  }
  public Stop getStop2() {
    return stop2;
  }
  public void setStop1(Stop stop1) {
    this.stop1 = stop1;
  }
  public void setStop2(Stop stop2) {
    this.stop2 = stop2;
  }
  public RStop getRStop1() {
    return new RStop(routeId, stop1);
  }
  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder();
    buf.append( (index+1) + " " );
    buf.append(routeId);
    buf.append( " " );
    buf.append(stop1.getName());
    if ( stop2 != null) {
      buf.append( " -> " );
      buf.append(stop2.getName());
    }
    return buf.toString();
  }        
}

