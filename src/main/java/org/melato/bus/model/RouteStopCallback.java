package org.melato.bus.model;

import java.util.List;

import org.melato.gpx.Point;

public interface RouteStopCallback {
  void add(RouteId routeId, List<Point> waypoints );
}
