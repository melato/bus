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

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.melato.bus.plan.PlanRequest;
import org.melato.gps.Point2D;

public class OTPRequest {
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
  
  public static String queryString(PlanRequest q) {
    StringBuilder buf = new StringBuilder();
    append(buf, "fromPlace", format(q.getFromPlace()));
    append(buf, "toPlace", format(q.getToPlace()));
    append(buf, "maxWalkDistance", q.getMaxWalkDistance());
    append(buf, "walkSpeed", q.getWalkSpeed());
    String[] dt = formatDateTime(q.getDate());
    append(buf, "time", dt[1]);
    append(buf, "date", dt[0]);
    append(buf, "arriveBy", q.isArriveBy());
    append(buf, "mode", formatMode(q.getMode()));
    append(buf, "min", q.getMin());
    return buf.toString();
  }
}
