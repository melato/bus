package org.melato.bus.model;

public class StopCount {
  public int stops;
  public int timedStops;
  @Override
  public String toString() {
    return timedStops + "/" + stops;
  }
  /** Count the number of stops that are timed within a range of stops.
   * @param stops
   * @param start The first stop to count.  Stop 0 should not be counted, so start from 1.
   * @param end The stop after the last one to count.
   * @return
   */
  public StopCount(Stop[] stops, int start, int end) {
    this.stops = end - start;
    if ( this.stops < 0 ) {
      this.stops = 0;
    }
    timedStops = 0;
    for( int i = start; i < end; i++ ) {
      if ( stops[i].isTimed() ) {
        timedStops++;
      }
    }
  }    
}
