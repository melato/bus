package org.melato.bus.model;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.melato.gps.Point;
import org.melato.gpx.Waypoint;

/**
 * Provides dummy implementations of non-essential RouteStorage methods.
 * @author Alex Athanasopoulos
 */
public abstract class AbstractRouteStorage implements RouteStorage {

  @Override
  public MarkerInfo loadMarker(String symbol) {
    throw new UnsupportedOperationException();
  }

  
  @Override
  public void iterateNearbyRoutes(Point point, float latitudeDifference,
      float longitudeDifference, Collection<RouteId> collector) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void iterateNearbyStops(Point point, float latDiff, float lonDiff,
      Collection<Waypoint> collector) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void iterateAllRouteStops(RouteStopCallback callback) {
    throw new UnsupportedOperationException();
  }

  
  @Override
  public String getUri(RouteId routeId) {
    return null;
  }


  @Override
  public List<Route> loadPrimaryRoutes() {
    return Collections.emptyList();
  }
    
}
