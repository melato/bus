package org.melato.bus.client;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

import org.melato.gpx.Earth;
import org.melato.gpx.Point;
import org.melato.gpx.Waypoint;

/** Contains a Waypoint and a distance.
 * The distance may be the route distance of the waypoint from the beginning of a route,
 * or the distance between the waypoint and the current location.
 * */
public class WaypointDistance implements Comparable<WaypointDistance> {
  private Waypoint  waypoint;
  private float      distance;
  static DecimalFormat kmFormat = new DecimalFormat( "0.00" );
  
  public WaypointDistance(Waypoint waypoint, float distance) {
    this.waypoint = waypoint;
    this.distance = distance;
  }
  
  public Waypoint getWaypoint() {
    return waypoint;
  }
    
  public float getDistance() {
    return distance;
  }
  
  public void setDistance(float distance) {
    this.distance = distance;
  }

  public static String formatDistance(float distance) {
    if ( Math.abs(distance) < 1000 ) {
      return String.valueOf( Math.round(distance)) + "m";
    } else {
      return kmFormat.format(distance/1000) + "Km";
    }
  }
  
  public static int compare(WaypointDistance x, WaypointDistance y) {
    if ( x.distance < y.distance )
      return -1;
    if ( x.distance > y.distance )
      return 1;
    return 0;
  }

  @Override
  public int compareTo(WaypointDistance x) {
    return compare(this, x);
  }

  @Override
  public String toString() {
    return waypoint.getName() + " " + formatDistance(distance);
  }
  
  /**
   * Order waypoints according to closest distance to target. 
   * @param waypoints
   * @param target
   * @return
   */
  public static Waypoint[] sort(List<Waypoint> waypoints, Point target) {
    WaypointDistance[] array = createArray(waypoints, target );
    Arrays.sort(array);
    Waypoint[] result = new Waypoint[array.length];
    for( int i = 0; i < array.length; i++ ) {
      result[i] = array[i].getWaypoint();
    }
    return result;
  }

  public static void setDistance(WaypointDistance[] array, Point location) {
    for(WaypointDistance stop: array ) {
      stop.setDistance(Earth.distance(stop.getWaypoint(), location));          
    }
  }  
  
  public static WaypointDistance[] createArray(List<Waypoint> waypoints, Point target) {
    WaypointDistance[] array = new WaypointDistance[waypoints.size()];
    for( int i = 0; i < array.length; i++ ) {
      Waypoint p = waypoints.get(i); 
      float distance = target != null ? Earth.distance(p, target) : 0;
      array[i] = new WaypointDistance(p, distance);
    }
    return array;
  }
}
