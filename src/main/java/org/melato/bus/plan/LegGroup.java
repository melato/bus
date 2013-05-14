package org.melato.bus.plan;

import java.io.Serializable;
import java.util.List;

import org.melato.bus.model.RStop;
import org.melato.bus.model.RouteId;
import org.melato.bus.model.RouteManager;
import org.melato.bus.model.Stop;

/** A leg is a portion of a route between two stops. */ 
public class LegGroup implements Serializable {
  private static final long serialVersionUID = 1L;
  public Leg leg;
  private Leg[] equivalentLegs;
  
  public Leg getLeg() {
    return leg;
  }
  public Leg[] getLegs() {
    return equivalentLegs;
  }
  public LegGroup(Leg leg) {
    super();
    this.leg = leg;
  }  
  
  public RouteId getRouteId() {
    return leg.getRouteId();
  }
  public Stop getStop1() {
    return leg.getStop1();
  }
  public Stop getStop2() {
    return leg.getStop2();
  }
  public void setStop1(Stop stop1) {
    leg.setStop1(stop1);
    equivalentLegs = null;
  }
  public void setStop2(Stop stop2) {
    leg.setStop2(stop2);
    equivalentLegs = null;
  }
  public RStop getRStop1() {
    return leg.getRStop1();
  }
  Leg[] findEquivalentLegs(RouteManager routeManager) {
    if ( leg.getStop2() != null) {
      List<Leg> legs = routeManager.getLegsBetween(leg.getStop1().getSymbol(), leg.getStop2().getSymbol());
      return legs.toArray(new Leg[0]);
    } else {
      return new Leg[] {leg};
    }
  }
  public Leg[] getEquivalentLegs(RouteManager routeManager) {
    if ( equivalentLegs == null) {
      equivalentLegs = findEquivalentLegs(routeManager);
    }
    return equivalentLegs;
  }
  @Override
  public String toString() {
    return leg.toString();
  }
  
}

