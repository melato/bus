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
  public static final float CLOSE_DISTANCE = 100;
  private List<Leg> legs;
  
  public List<Leg> getLegs() {
    return legs;
  }

  public void setLegs(List<Leg> legs) {
    this.legs = legs;
  }

  
  public Sequence() {
    super();
    legs = new ArrayList<Leg>();
    
  }

  public Sequence(List<Leg> legs) {
    super();
    this.legs = legs;
  }


  public static class Leg implements Serializable {
    public RouteId routeId;
    public Stop stop1;
    public Stop stop2;
    public Leg(RStop stop) {
      this.routeId = stop.getRouteId();
      this.stop1 = stop.getStop();      
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
    @Override
    public String toString() {
      StringBuilder buf = new StringBuilder();
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
  
  public void addStop(RouteManager routeManager, RStop stop) {
    if ( legs.isEmpty() ) {
      legs.add(new Leg(stop));
      return;
    }
    Leg lastLeg = legs.get(legs.size()-1);
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
      } else {
        legs.add(new Leg(stop));
      }
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
