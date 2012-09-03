package org.melato.bus.model;

import java.util.Collection;

import org.melato.gpx.Point;
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
  public void iterateNearbyStops(Point point, float distance,
      Collection<Waypoint> collector) {
    throw new UnsupportedOperationException();
  }

  @Override
  public String getUri(Route route) {
    return null;
  }

  
}
