package org.melato.bus.model.cache;

public class GpsRectangle implements Cloneable {
  /** The minimum displayed latitude */
  public float latMin;
  /** The maximum displayed latitude */
  public float latMax;
  /** The minimum displayed longitude */
  public float lonMin;
  /** The maximum displayed longitude */
  public float lonMax;
  /** The routes currently displayed. */
  @Override
  public GpsRectangle clone() {
    try {
      return (GpsRectangle) super.clone();
    } catch (CloneNotSupportedException e) {
      throw new RuntimeException(e);
    }
  }
  
}
