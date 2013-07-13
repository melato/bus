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
package org.melato.bus.otp;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.melato.gps.Point2D;

/*
http://localhost:8080/opentripplanner-api-webapp/ws/plan
  ?fromPlace=37.9890,23.7906
  &toPlace=37.939693,23.656956
  &mode=BUS,WALK
  &min=TRANSFERS
  &maxWalkDistance=1260
  &walkSpeed=1.341
  &time=9:00pm
  &date=7/7/2013
  &arriveBy=true
  
  &itinID=1
  &wheelchair=false
  &preferredRoutes=
  &unpreferredRoutes=
 */
public class OTPRequest {
  public static final String BUS = "BUS";
  public static final String WALK = "WALK";
  public static final String MIN_TRANSFERS = "TRANSFERS";
  Point2D fromPlace;
  Point2D toPlace;
  int maxWalkDistance = 1000;
  float walkSpeed = 5 / 3.6f;
  Date date = new Date();
  boolean arriveBy;
  private List<String> mode = new ArrayList<String>();
  private String min = MIN_TRANSFERS;
  
  public OTPRequest() {
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
  private static String formatMode(List<String> modes) {
    StringBuilder buf = new StringBuilder();
    int size = modes.size();
    for( int i = 0; i < size; i++ ) {
      if ( i > 0 ) {
        buf.append(",");        
      }
      buf.append(modes.get(i));
    }
    return buf.toString();
  }
  private static String[] formatDateTime(Date date) {
    System.out.println( "formatDateTime: " + date );
    Calendar cal = new GregorianCalendar();
    cal.setTime(date);
    String[] fields = new String[2];
    fields[0] = (cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.DAY_OF_MONTH ) + "/" + cal.get(Calendar.YEAR );
    int hour = cal.get(Calendar.HOUR);
    if ( hour == 0 )
      hour = 12;
    String ampm = cal.get(Calendar.AM_PM) == Calendar.PM ? "pm" : "am";
    fields[1] = hour + ampm;  
    return fields;
  }
  private static String format(Point2D point) {
    return point.getLat() + "," + point.getLon();
  }
  private static void append(StringBuilder buf, String key, Object value) {
    if ( buf.length() > 0 ) {
      buf.append( "&");     
    }
    buf.append( key);
    buf.append("=");
    buf.append( String.valueOf(value));
  }
  
  public String queryString() {
    StringBuilder buf = new StringBuilder();
    append(buf, "fromPlace", format(fromPlace));
    append(buf, "toPlace", format(toPlace));
    append(buf, "maxWalkDistance", maxWalkDistance);
    append(buf, "walkSpeed", walkSpeed);
    String[] dt = formatDateTime(date);
    append(buf, "time", dt[1]);
    append(buf, "date", dt[0]);
    append(buf, "arriveBy", arriveBy);
    append(buf, "mode", formatMode(mode));
    append(buf, "min", min);
    return buf.toString();
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
  public boolean isArriveBy() {
    return arriveBy;
  }
  public void setArriveBy(boolean arriveBy) {
    this.arriveBy = arriveBy;
  }
  
  
}
