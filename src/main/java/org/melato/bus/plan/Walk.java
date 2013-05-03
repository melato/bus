package org.melato.bus.plan;

import java.text.DecimalFormat;

import org.melato.bus.client.Formatting;
import org.melato.gps.GlobalDistance;
import org.melato.gps.Point2D;

public class Walk implements LegItem {
  public static final float OVERHEAD = 1.3f;
  public static final float SPEED = 5000f/3600;
  public static final DecimalFormat D2 = new DecimalFormat("00");
  private float distance;
  public Walk(Point2D point1, Point2D point2) {
    super();
    distance = new GlobalDistance().distance(point1, point2);
  }
  public static String formatDuration(float time) {
    int seconds = (int) time;
    if ( seconds >= 3600 ) {
      return D2.format(seconds/3600) + ":" + D2.format(seconds%60);
    } else {
      return D2.format(seconds/60) + "'";
    }
  }
  
  @Override
  public String toString() {
    return "Walk " + Formatting.straightDistance(distance) + " " + formatDuration(distance*OVERHEAD/SPEED);
  }
}
