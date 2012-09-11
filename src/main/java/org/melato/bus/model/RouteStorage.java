package org.melato.bus.model;

import java.util.Collection;
import java.util.List;

import org.melato.gpx.Point;
import org.melato.gpx.Waypoint;

/**
 * Provides low-level database access to routes.
 * @author Alex Athanasopoulos
 *
 */
public interface RouteStorage {
  /** Load a list of all routes. */
  List<Route> loadRoutes();
  /** Load a single route. */
  Route loadRoute(RouteId routeId);
  /** Load a route's schedule (for all days of the week) */
  Schedule loadSchedule(RouteId routeId);
  /** Load a route's stops as a list of consecutive waypoints. */
  List<Waypoint> loadWaypoints(RouteId routeId);
  /** Load information about a single stop. */
  MarkerInfo loadMarker(String symbol);
  /** Load stops that are near a certain point.
   * The waypoint's links should contain the relevant route-ids.
   * */
  void iterateNearbyStops(Point point, float distance, Collection<Waypoint> collector);
  /**
   * Return the original web URL for the route at the route provider's web site.
   * @param route
   * @return
   */
  String getUri(RouteId route);
}
