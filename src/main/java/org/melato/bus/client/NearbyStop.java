/*-------------------------------------------------------------------------
 * Copyright (c) 2012,2013, Alex Athanasopoulos.  All Rights Reserved.
 * alex@melato.org
 *-------------------------------------------------------------------------
 * This file is part of Athens Next Bus
 *
 * Athens Next Bus is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Athens Next Bus is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Athens Next Bus.  If not, see <http://www.gnu.org/licenses/>.
 *-------------------------------------------------------------------------
 */
package org.melato.bus.client;

import java.util.Comparator;
import java.util.Date;

import org.melato.bus.model.RStop;
import org.melato.bus.model.Route;
import org.melato.bus.model.Schedule;

/** Maintains information about a bus stop nearby. */
public class NearbyStop {
  private Route     route;
  private RStop     rstop;
  private int       group;
  private int[]     nearestTimes; // the nearest time and the next one.

  public static class Comparer implements Comparator<NearbyStop> {
    int baseTime;

    public Comparer() {
      baseTime = Schedule.getTime(new Date());
    }
    boolean hasTime(NearbyStop s) {
      return s.nearestTimes != null && s.nearestTimes.length > 0;
    }
    int getSortTime(NearbyStop s) {
      int[] times = s.nearestTimes;
      for(int i = 0; i < times.length; i++ ) {
        if ( times[i] >= baseTime ) {
          return times[i];
        }
      }
      if ( times.length >= 0 ) {
        return 24*60 + times[0]; 
      }
      return 24*60*2;
    }
    @Override
    public int compare(NearbyStop s1, NearbyStop s2) {
      int d = RStop.compare(s1.rstop,  s2.rstop);
      if ( d != 0 )
        return d;
      if ( hasTime(s1) && hasTime(s2)) {
        return getSortTime(s1) - getSortTime(s2);
      }
      if ( hasTime(s1) && ! hasTime(s2)) {
        return -1;
      }
      if ( ! hasTime(s1) && hasTime(s2)) {
        return 1;
      }
      // when two routes have the same stop, compare them by name.
      return s1.route.compareTo(s2.route);
    }
    
  }
  public NearbyStop(RStop rstop, Route route) {
    this.rstop = rstop;
    this.route = route;
  }
  
  public Route getRoute() {
    return route;
  }

  public int getGroup() {
    return group;
  }

  public void setGroup(int group) {
    this.group = group;
  }

  
  public RStop getRStop() {
    return rstop;
  }

  @Override
  public String toString() {    
    StringBuilder buf = new StringBuilder();
    buf.append( route );
    buf.append( " " );
    buf.append( rstop.getStop().getName());
    buf.append( " (");
    buf.append( RStop.formatDistance(rstop.getDistance()));
    buf.append( ")" );
    if ( nearestTimes != null) {
      for( int time: nearestTimes ) {
        buf.append( " " );
        buf.append( Schedule.formatTimeMod24(time));
      }
    }
    return buf.toString();
  }

  public int[] getNearestTimes() {
    return nearestTimes;
  }

  public void setNearestTimes(int[] nearestTimes) {
    this.nearestTimes = nearestTimes;
  }
}
