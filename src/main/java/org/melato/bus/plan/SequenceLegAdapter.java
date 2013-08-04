package org.melato.bus.plan;

import org.melato.bus.model.Route;
import org.melato.bus.model.RouteManager;

public class SequenceLegAdapter implements LegAdapter {
  private SequenceItinerary.Leg leg;
  private SequenceItinerary.TransitLeg transit;
  private SequenceItinerary.WalkLeg walk;
  private RouteManager routeManager;

  
  public SequenceLegAdapter(SequenceItinerary.Leg leg, RouteManager routeManager) {
    super();
    this.routeManager = routeManager;
    this.leg = leg;
    if ( leg instanceof SequenceItinerary.TransitLeg ) {
      transit = (SequenceItinerary.TransitLeg) leg;
    }
    if ( leg instanceof SequenceItinerary.WalkLeg ) {
      walk = (SequenceItinerary.WalkLeg) leg;
    }
  }

  @Override
  public String getLabel() {
    Route route = routeManager.getRoute(transit.leg.routeId);
    return route.getLabel();
  }

  @Override
  public String getFromName() {
    return transit.leg.getStop1().getName();
  }

  @Override
  public String getToName() {
    return transit.leg.getStop2().getName();
  }

  @Override
  public int getStartTime() {
    return leg.getStartTime();
  }

  @Override
  public int getEndTime() {
    return leg.getEndTime();
  }

  @Override
  public int getDuration() {
    return leg.getDuration();
  }

  @Override
  public int getDiffTime() {
    return transit != null ? transit.diffTime : -1;
  }

  @Override
  public float getDistance() {
    return leg.distance;
  }

  @Override
  public boolean isWalk() {
    return walk != null;
  }
}

