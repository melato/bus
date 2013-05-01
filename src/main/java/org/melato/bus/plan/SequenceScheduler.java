/*-------------------------------------------------------------------------
 * Copyright (c) 2012,2013, Alex Athanasopoulos.  All Rights Reserved.
 * alex@melato.org
 *-------------------------------------------------------------------------
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *-------------------------------------------------------------------------
 */
package org.melato.bus.plan;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.melato.bus.model.DaySchedule;
import org.melato.bus.model.RouteManager;
import org.melato.bus.model.Schedule;
import org.melato.util.DateId;

public class SequenceScheduler {
  private int dateId;
    
  public void setDateId(int dateId) {
    this.dateId = dateId;
  }

  static LegTime findPrevious(LegTime[] legTimes, int index) {
    int legIndex = legTimes[index].leg.index;
    if ( legIndex > 0 ) {
      legIndex--;
      for( int i = index - 1; i >= 0; i-- ) {
        if ( legTimes[i].leg.index == legIndex) {
          return legTimes[i];
        }
      }
    }
    return null;
  }
  public LegTime[] schedule(Sequence sequence, RouteManager routeManager) {
    Date date = null;
    if ( dateId != 0 ) {
      date = DateId.getDate(dateId);
      System.out.println( "Date: " + DateId.toString(dateId));
    } else {
      date = new Date();
    }
    Leg[] legs = new Leg[sequence.getLegs().size()];
    for( int i = 0; i < legs.length; i++ ) {
      legs[i] = sequence.getLegs().get(i);
    }
    int legIndex = -1;
    if ( legs.length > 0 ) {
      legIndex = legs[legs.length-1].index;
    }
    List<LegTime> timeList = new ArrayList<LegTime>();
    for(int i = 0; i < legs.length; i++ ) {
      Leg leg = legs[i];
      Schedule schedule = routeManager.getSchedule(leg.getRouteId());
      DaySchedule daySchedule = schedule.getSchedule(date);
      int[] times = daySchedule.getTimes();
      for( int time: times ) {
        LegTime legTime = new LegTime(leg, time, routeManager);
        timeList.add(legTime);
      }
    }
    LegTime[] timeArray = timeList.toArray(new LegTime[0]);
    Arrays.sort(timeArray, new LegTimeComparator());
    for( int i = 0; i < timeArray.length; i++ ) {
      LegTime legTime = timeArray[i];
      legTime.previous = findPrevious(timeArray, i);
      if ( legTime.leg.index == legIndex ) {
        legTime.last = true;
      }      
    }
    return timeArray;
  }
}
