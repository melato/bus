package org.melato.bus.model;

import java.util.ArrayList;
import java.util.List;

import org.melato.gpx.GPX;
import org.melato.gpx.Point;
import org.melato.gpx.Waypoint;


/**
 * Provides read-access to routes, backed by a pluggable route database (RouteStorage).
 * For use on Java SE or Android
 * @author Alex Athanasopoulos
 *
 */
public class RouteManager {
  private RouteStorage storage;  
    
  public RouteManager(RouteStorage storage) {
    super();
    this.storage = storage;
  }
  
  public List<Route> getRoutes() {
    return storage.loadRoutes();
  }

  public Route getRoute(RouteId routeId) {
    return storage.loadRoute(routeId);
  }
  
  public Route loadRoute(RouteId routeId) {
    return storage.loadRoute(routeId);
  }

  Route loadRoute(Route route) {
    return loadRoute(route.getRouteId());
  }

  public GPX loadGPX(RouteId routeId) {
    return storage.loadGPX(routeId);
  }

  public GPX loadGPX(Route route) {
    return loadGPX(route.getRouteId());
  }

  public String getUri( Route route ) {
    return storage.getUri(route.getRouteId());
  }
  /**
   * Load marker information:
   * - waypoint (location, name)
   * - linked routes
   * - 
   * @param symbol
   * @return
   */
  public MarkerInfo loadMarker(String symbol) {
    return storage.loadMarker(symbol);
  }
  
  public List<Waypoint> findNearbyStops(Point point, float distance) {
    List<Waypoint> result = new ArrayList<Waypoint>();
    storage.iterateNearbyStops(point, distance, result);
    return result;
  }
}
