package org.melato.bus.model;

import java.util.List;

import org.melato.gpx.Waypoint;

public class MarkerInfo {
  Waypoint  waypoint;
  List<Route> routes;
  public Waypoint getWaypoint() {
    return waypoint;
  }
  public List<Route> getRoutes() {
    return routes;
  }
  public MarkerInfo(Waypoint waypoint, List<Route> routes) {
    super();
    this.waypoint = waypoint;
    this.routes = routes;
  }
}
