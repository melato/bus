package org.melato.bus.model;

import java.io.Serializable;
import java.text.DecimalFormat;

/**
 * A stop on a route.
 * (Will probably merge with RouteStop)
 * @author Alex Athanasopoulos
 *
 */
public class RStop implements Serializable, Comparable<RStop> {
  private static final long serialVersionUID = 1L;
  private static DecimalFormat kmFormat = new DecimalFormat( "0.00" );
  private RouteId routeId;
  private Stop    stop;
  private int     stopIndex;
  private float   distance;
  public RStop(RouteId routeId, Stop stop, int stopIndex) {
    super();
    this.routeId = routeId;
    this.stop = stop;
    this.stopIndex = stopIndex;
  }
  public RouteId getRouteId() {
    return routeId;
  }
  public Stop getStop() {
    return stop;
  }
  public int getStopIndex() {
    return stopIndex;
  }
  public float getDistance() {
    return distance;
  }
  public void setDistance(float distance) {
    this.distance = distance;
  }  
  public static int compare(RStop x, RStop y) {
    if ( x.distance < y.distance )
      return -1;
    if ( x.distance > y.distance )
      return 1;
    return 0;
  }

  public static String formatDistance(float distance) {
    if ( Math.abs(distance) < 1000 ) {
      return String.valueOf( Math.round(distance)) + "m";
    } else {
      return kmFormat.format(distance/1000) + "Km";
    }
  }
  @Override
  public int compareTo(RStop o) {
    return compare(this, o);
  }
}
