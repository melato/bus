package org.melato.bus.model;

import java.util.List;

import org.melato.gpx.Waypoint;

/**
 * Provides information about a system stop, which includes all the connected routes.
 * @author Alex Athanasopoulos
 *
 */
public class MarkerInfo {
  private Waypoint  waypoint;
  private List<Route> routes;
  /** Return the stop's waypoint.
   * In addition to lat, lon, sym, name, the waypoint has a link for each of its routes, with the route-id.
   * @return
   */
  public Waypoint getWaypoint() {
    return waypoint;
  }
  
  /** 
   * Return the list of routes that include this marker as a stop.
   * @return
   */
  public List<Route> getRoutes() {
    return routes;
  }
  public MarkerInfo(Waypoint waypoint, List<Route> routes) {
    super();
    this.waypoint = waypoint;
    this.routes = routes;
  }
}
