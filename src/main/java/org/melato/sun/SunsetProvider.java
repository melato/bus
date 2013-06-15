package org.melato.sun;

import java.util.Date;

/** Provides sunrise/sunset times (for a particular location) */
public interface SunsetProvider {
  /** Get the sunrise and sunset times for a particular date.
   * @param date  The requested date
   * @return an array of two times, in minutes since midnight, or null if it is not known.
   * The first time is for the sunrise and the 2nd one for the sunset.
   */
  int[] getSunriseSunset(Date date);
}
