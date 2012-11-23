package org.melato.bus.model;

import org.melato.gps.Point2D;
import org.melato.gps.PointTime;

/** A route stop.
 * consider merging with RouteStop.  
 * @author Alex Athanasopoulos
 */
public class Stop extends PointTime {
  private static final long serialVersionUID = 1L;
  String name;
  String symbol;
  float deviation = 1;
  public Stop() {
    super();
  }
  public Stop(float lat, float lon) {
    super(lat, lon);
  }
  
  public Stop(Point2D p) {
    super(p);
  }
  public Stop(PointTime p) {
    super(p);
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getSymbol() {
    return symbol;
  }
  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }
  public float getDeviation() {
    return deviation;
  }
  public void setDeviation(float deviation) {
    this.deviation = deviation;
  }
}
