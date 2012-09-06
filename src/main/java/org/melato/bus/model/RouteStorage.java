package org.melato.bus.model;

import java.util.Collection;
import java.util.List;

import org.melato.gpx.GPX;
import org.melato.gpx.Point;
import org.melato.gpx.Waypoint;

/**
 * Provides low-level database access to routes.
 * @author Alex Athanasopoulos
 *
 */
public interface RouteStorage {
  List<Route> loadRoutes();
  Route loadRoute(RouteId routeId);
  GPX loadGPX(RouteId routeId);
  MarkerInfo loadMarker(String symbol);
  void iterateNearbyStops(Point point, float distance, Collection<Waypoint> collector);
  String getUri(RouteId route);
}
