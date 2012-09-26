package org.melato.bus.model;

import java.util.List;

import org.melato.gps.Point;

public interface RouteStopCallback {
  void add(RouteId routeId, List<Point> waypoints );
}
