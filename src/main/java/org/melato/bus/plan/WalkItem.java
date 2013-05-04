package org.melato.bus.plan;

import org.melato.bus.client.Formatting;
import org.melato.gps.GlobalDistance;
import org.melato.gps.Point2D;

public class WalkItem implements LegItem {
  private float distance;
  public WalkItem(Point2D point1, Point2D point2) {
    super();
    distance = new GlobalDistance().distance(point1, point2);
  }
  @Override
  public String toString() {
    return "Walk " + Formatting.straightDistance(distance) + " " + Walk.distanceDuration(distance);
  }
}
