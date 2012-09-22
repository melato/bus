package org.melato.bus.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.melato.gpx.Earth;
import org.melato.gpx.GPX;
import org.melato.gpx.Point;
import org.melato.gpx.Sequence;
import org.melato.gpx.Waypoint;
import org.melato.log.Clock;
import org.melato.log.Log;
import org.melato.util.AbstractCollector;


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
  
  public synchronized List<Route> getRoutes() {
    Clock clock = new Clock("getRoutes");
    try {
      return storage.loadRoutes();
    } finally {
      Log.info(clock);
    }
  }
  
  public synchronized Route getRoute(RouteId routeId) {
    return storage.loadRoute(routeId);
  }
  
  public synchronized Route loadRoute(RouteId routeId) {
    return storage.loadRoute(routeId);
  }

  public synchronized Schedule loadSchedule(RouteId routeId) {
    Log.info( "RouteManager.loadSchedule: " + routeId );
    return storage.loadSchedule(routeId);
  }

  public synchronized Schedule loadSchedule(Route route) {
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
  public synchronized List<Waypoint> loadWaypoints(RouteId routeId) {
    return storage.loadWaypoints(routeId);
  }

  public synchronized List<Waypoint> loadWaypoints(Route route) {
    return storage.loadWaypoints(route.getRouteId());
  }

  public synchronized GPX loadGPX(RouteId routeId) {
    List<Waypoint> waypoints = storage.loadWaypoints(routeId);
    GPX gpx = new GPX();
    org.melato.gpx.Route rte = new org.melato.gpx.Route();
    rte.path = new Sequence(waypoints);
    gpx.getRoutes().add(rte);
    return gpx;      
  }

  public synchronized GPX loadGPX(Route route) {
    return loadGPX(route.getRouteId());
  }

  public synchronized String getUri( Route route ) {
    return storage.getUri(route.getRouteId());
  }
  /**
   * Load marker information, which includes all routes that go through the given stop.
   * @param symbol
   * @return
   */
  public synchronized MarkerInfo loadMarker(String symbol) {
    return storage.loadMarker(symbol);
  }

  static class DistanceFilter extends AbstractCollector<Waypoint> {
    Collection<Waypoint> result;
    
    private Point center;
    private float distance;
    
    public DistanceFilter(List<Waypoint> result, Point center, float distance) {
      super();
      this.result = result;
      this.center = center;
      this.distance = distance;
    }

    @Override
    public boolean add(Waypoint p) {
      if ( Earth.distance(center, p) < distance ) {
        result.add(p);
        size++;
        return true;
      }
      return false;
    }    
  }
  
  public synchronized void iterateNearbyRoutes(Point point, float latitudeDifference,
      float longitudeDifference, Collection<RouteId> collector) {
    storage.iterateNearbyRoutes(point, latitudeDifference, longitudeDifference,
        collector);
  }

  public synchronized List<Waypoint> findNearbyStops(Point point, float distance) {
    List<Waypoint> result = new ArrayList<Waypoint>();
    DistanceFilter filter = new DistanceFilter(result, point, distance);
    float latDiff = Earth.latitudeForDistance(distance);
    float lonDiff = Earth.longitudeForDistance(distance, point.getLat());
    storage.iterateNearbyStops(point, latDiff, lonDiff, filter);
    return result;
  }

  

  public synchronized void iterateAllRouteStops(RouteStopCallback callback) {
    storage.iterateAllRouteStops(callback);
  }

  public synchronized void benchmark() {
    iterateAllRouteStops(new RouteStopCallback() {

      @Override
      public void add(RouteId routeId, List<Point> waypoints) {
      }
      
    });
  }

  public RouteStorage getStorage() {
    return storage;
  }  
}
