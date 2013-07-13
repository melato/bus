package org.melato.bus.plan;

import org.melato.gps.Point2D;

public class NamedPoint extends Point2D {
  private static final long serialVersionUID = 1L;
  private String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public NamedPoint(Point2D point) {
    super(point);
  }

  public NamedPoint() {
    super();
  }

  @Override
  public String toString() {
    if ( name != null) {
      return name;
    }
    return super.toString();
  }
  
  
}
