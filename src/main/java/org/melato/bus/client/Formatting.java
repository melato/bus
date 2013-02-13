package org.melato.bus.client;

import java.text.DecimalFormat;

/** Various static methods to do formatting. */
public class Formatting {
  public static final DecimalFormat KM = new DecimalFormat( "0.00" );
  
  public static String distance(float distance) {
    if ( Math.abs(distance) < 1000 ) {
      return String.valueOf( Math.round(distance)) + "m";
    } else {
      return KM.format(distance/1000) + "Km";
    }
  }
  
  public static String straightDistance(float distance) {
    return "(" + distance(distance) + ")";
  }

  /** Set the bearing in the range (-180,180] */
  public static float normalizeBearing(float degrees) {
    degrees += 180; // adjust this between (0, 360]
    degrees = degrees % 360;
    if ( degrees <= 0 )
      degrees += 360;
    degrees -= 180;
    return degrees;
  }
  
  public static String bearing(float bearing) {
    bearing = normalizeBearing(bearing);
    return Math.round(bearing) + "°"; // degrees, unicode "\u00B0"
  }

  public static String routeDistance(float distance) {
    if ( Float.isNaN(distance)) {
      return "";
    }
    String sign = distance >= 0 ? "+" : "-"; 
    return sign + distance(Math.abs(distance));
  }
  
  public static String degrees(float degrees) {
    return String.valueOf(degrees);
  }
  
}
