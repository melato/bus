package org.melato.bus.plan;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.melato.bus.model.RStop;
import org.melato.bus.model.RouteId;
import org.melato.bus.model.RouteManager;
import org.melato.bus.model.Stop;
import org.melato.gps.Metric;
import org.melato.gps.Point2D;

public class Sequence implements Serializable {
  private static final long serialVersionUID = 2L;
  public static final float CLOSE_DISTANCE = 100;
  private List<LegGroup> legs;
  
  public List<LegGroup> getLegs() {
    return legs;
  }

  public void setLegs(List<LegGroup> legs) {
    this.legs = legs;
  }

  public List<SequenceItem> getSequenceItems() {
    List<SequenceItem> items = new ArrayList<SequenceItem>();
    Leg previous = null;
    for(LegGroup leg: legs ) {
      if ( previous != null) {
        items.add(new WalkItem(previous.getStop2(), leg.getLeg().getStop1()));
      }
      items.add(leg);
      previous = leg.getLeg();
    }
    return items;
  }
  
  public Sequence() {
    super();
    legs = new ArrayList<LegGroup>();
    
  }

  public Sequence(List<LegGroup> legs) {
    super();
    this.legs = legs;
  }


  public void addStopAfter(RouteManager routeManager, RStop stop) {
    if ( legs.isEmpty() ) {
      legs.add(new LegGroup(new Leg(stop)));
      return;
    }
    Leg lastLeg = legs.get(legs.size()-1).getLeg();
    if ( lastLeg.getRouteId().equals(stop.getRouteId())) {
      // if the last leg is for the same route.
      if ( lastLeg.getStop1().isBefore(stop.getStop())) {
        // replace stop2
        lastLeg.setStop2(stop.getStop());
      } else {
        // or replace stop1
        lastLeg.setStop1(stop.getStop());
      }
    } else {
      // add a new leg
      if ( lastLeg.getStop2() == null) {
        // we need to complete the previous leg,
        // by finding the intersection of the previous route and the new route        
        Stop s = findClosestStopAfterStop(routeManager, lastLeg.getRouteId(), lastLeg.getStop1(), stop.getStop());
        lastLeg.setStop2(s);
      }
      Leg leg = new Leg(stop);
      legs.add(new LegGroup(leg));
    }
  }
  
  public void addStopBefore(RouteManager routeManager, RStop stop) {
    if ( legs.isEmpty() ) {
      legs.add(new LegGroup(new Leg(stop.getRouteId(), stop.getStop(), stop.getStop())));
      return;
    }
    Leg firstLeg = legs.get(0).getLeg();
    if ( firstLeg.getRouteId().equals(stop.getRouteId())) {
      // if the first leg is for the same route.
      if ( stop.getStop().isBefore(firstLeg.getStop2())) {
        // replace stop1
        firstLeg.setStop1(stop.getStop());
      } else {
        // or replace both stops
        firstLeg.setStop1(stop.getStop());
        firstLeg.setStop2(stop.getStop());
      }
    } else {
      // add a new leg at the beginning
      Leg leg = new Leg(stop.getRouteId(), stop.getStop(), stop.getStop());
      legs.add(0, new LegGroup(leg));
    }
  }
  
  public static Stop findClosestStopAfterStop(RouteManager routeManager, RouteId routeId, Stop stop, Point2D point) {
    Metric metric = routeManager.getMetric();
    Stop[] stops = routeManager.getStops(routeId);
    int minIndex = -1;
    float minDistance = 0; 
    for( int i = stop.getIndex(); i < stops.length; i++ ) {
      float d = metric.distance(stops[i], point);
      if ( minIndex < 0 || d < minDistance) {
        minIndex = i;
        minDistance = d;
      } else {
        if ( minDistance < CLOSE_DISTANCE ) {
          break;
        }
      }
    }
    if ( minIndex == -1 )
      return stop;
    return stops[minIndex];    
  }  
}
