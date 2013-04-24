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
package org.melato.bus.plan;

import java.util.Comparator;

import org.melato.bus.model.RStop;



public class StopPair {
  private RStop stop1;
  private RStop stop2;
  private float distance;
  private float time;
  
  /** Sort by route id. */
  public static class RouteComparator implements Comparator<StopPair> {
    @Override
    public int compare(StopPair s1, StopPair s2) {
      return s1.getStop1().getRouteId().compareTo(s2.getStop1().getRouteId());
    }    
  }
  
  public StopPair(RStop stop1, RStop stop2) {
    super();
    this.stop1 = stop1;
    this.stop2 = stop2;
  }
  public RStop getStop1() {
    return stop1;
  }
  public RStop getStop2() {
    return stop2;
  }
  public float getDistance() {
    return distance;
  }
  public void setDistance(float distance) {
    this.distance = distance;
  }
  public float getTime() {
    return time;
  }
  public void setTime(float time) {
    this.time = time;
  }  
}
