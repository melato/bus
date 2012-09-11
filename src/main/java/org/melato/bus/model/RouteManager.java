package org.melato.bus.model;

import java.util.ArrayList;
import java.util.List;

import org.melato.gpx.GPX;
import org.melato.gpx.Point;
import org.melato.gpx.Sequence;
import org.melato.gpx.Waypoint;
import org.melato.log.Clock;
import org.melato.log.Log;


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
    Clock clock = new Clock("getRoutes");
    try {
      return storage.loadRoutes();
    } finally {
      Log.info(clock);
    }
  }
  
  public Route getRoute(RouteId routeId) {
    return storage.loadRoute(routeId);
  }
  
  public Route loadRoute(RouteId routeId) {
    return storage.loadRoute(routeId);
  }

  public Schedule loadSchedule(RouteId routeId) {
    Log.info( "RouteManager.loadSchedule: " + routeId );
    return storage.loadSchedule(routeId);
  }

  public Schedule loadSchedule(Route route) {
    return loadSchedule(route.getRouteId());
  }

  /**
   * Get the list or waypoints for the route.
   * Each waypoint is a route stop.
   * It defines:
   *  - lat, lon - The stops coordinates
   *  - sym - The stop symbol
   *  - name - The stop label  
   * */
  public List<Waypoint> loadWaypoints(RouteId routeId) {
    return storage.loadWaypoints(routeId);
  }

  public List<Waypoint> loadWaypoints(Route route) {
    return storage.loadWaypoints(route.getRouteId());
  }

  public GPX loadGPX(RouteId routeId) {
    List<Waypoint> waypoints = storage.loadWaypoints(routeId);
    GPX gpx = new GPX();
    org.melato.gpx.Route rte = new org.melato.gpx.Route();
    rte.path = new Sequence(waypoints);
    gpx.getRoutes().add(rte);
    return gpx;      
  }

  public GPX loadGPX(Route route) {
    return loadGPX(route.getRouteId());
  }

  public String getUri( Route route ) {
    return storage.getUri(route.getRouteId());
  }
  /**
   * Load marker information, which includes all routes that go through the given stop.
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
