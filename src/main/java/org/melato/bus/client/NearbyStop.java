package org.melato.bus.client;

import java.util.Comparator;

import org.melato.bus.model.Route;
import org.melato.gpx.Waypoint;

/** Maintains information about a bus stop nearby. */
public class NearbyStop extends WaypointDistance {
  private Route     route;

  public static class Comparer implements Comparator<NearbyStop> {

    @Override
    public int compare(NearbyStop s1, NearbyStop s2) {
      int d = WaypointDistance.compare(s1,  s2);
      if ( d != 0 )
        return d;
      // when two routes have the same stop, compare them by name.
      return s1.route.compareTo(s2.route);
    }
    
  }
  public NearbyStop(Waypoint waypoint, Route route) {
    super(waypoint, 0f);
    this.route = route;
  }
  
  public Route getRoute() {
    return route;
  }

  @Override
  public String toString() {
    String s = route + " " + getWaypoint().getName() + " (" + formatDistance(getDistance()) + ")";
    return s;
  }
}
