package org.melato.bus.plan;

import java.io.Serializable;
import java.util.List;

import org.melato.bus.model.RouteManager;

/** A leg is a portion of a route between two stops. */ 
public class LegGroup implements SequenceItem, Serializable {
  private static final long serialVersionUID = 1L;
  public Leg leg;
  public Leg[] equivalentLegs;
  
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

