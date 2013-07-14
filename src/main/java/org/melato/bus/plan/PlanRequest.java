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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.melato.gps.Point2D;

/** A plan request.  It has fields used by OTP, but could be used by other planners as well. */
public class PlanRequest {
  public static final String BUS = "BUS";
  public static final String WALK = "WALK";
  public static final String MIN_TRANSFERS = "TRANSFERS";
  private Point2D fromPlace;
  private Point2D toPlace;
  private int maxWalkDistance = 1000;
  private float walkSpeed = 5 / 3.6f;
  private Date date = new Date();
  private int time = -1;
  private boolean arriveBy;
  private List<String> mode = new ArrayList<String>();
  private String min = MIN_TRANSFERS;
  
  public PlanRequest() {
    super();
    mode.add(BUS);
    mode.add(WALK);
  }
  public List<String> getMode() {
    return mode;
  }
  public void setMode(List<String> mode) {
    this.mode = mode;
  }
  
  public String getMin() {
    return min;
  }
  public void setMin(String min) {
    this.min = min;
  }
  public Point2D getFromPlace() {
    return fromPlace;
  }
  public void setFromPlace(Point2D fromPlace) {
    this.fromPlace = fromPlace;
  }
  public Point2D getToPlace() {
    return toPlace;
  }
  public void setToPlace(Point2D toPlace) {
    this.toPlace = toPlace;
  }
  public int getMaxWalkDistance() {
    return maxWalkDistance;
  }
  public void setMaxWalkDistance(int maxWalkDistance) {
    this.maxWalkDistance = maxWalkDistance;
  }
  public float getWalkSpeed() {
    return walkSpeed;
  }
  public void setWalkSpeed(float walkSpeed) {
    this.walkSpeed = walkSpeed;
  }
  public Date getDate() {
    return date;
  }
  public void setDate(Date date) {
    this.date = date;
  }  
  public void setTime(int time) {
    this.time = time;
  }
  public boolean isArriveBy() {
    return arriveBy;
  }
  public void setArriveBy(boolean arriveBy) {
    this.arriveBy = arriveBy;
  }
  
  
}
