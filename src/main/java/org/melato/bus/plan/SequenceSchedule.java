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
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.melato.bus.model.DaySchedule;
import org.melato.bus.model.RouteManager;
import org.melato.bus.model.Schedule;
import org.melato.util.DateId;

public class SequenceSchedule {
  private int dateId;
  private LegTime[] legs;
  private List<SequenceInstance> instances;
    
  public void setDateId(int dateId) {
    this.dateId = dateId;
  }
  
  public SequenceSchedule(Sequence sequence, RouteManager routeManager) {
    legs = createLegs(sequence, routeManager);
    instances = findInstances(legs);
  }
  public LegTime[] getLegs() {
    return legs;
  }

  public List<SequenceInstance> getInstances() {
    return instances;
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
  
  private LegTime[] createLegs(Sequence sequence, RouteManager routeManager) {
    Date date = null;
    if ( dateId != 0 ) {
      date = DateId.getDate(dateId);
    } else {
      date = new Date();
    }
    Leg[] legs = new Leg[sequence.getLegs().size()];
    for( int i = 0; i < legs.length; i++ ) {
      legs[i] = sequence.getLegs().get(i);
    }
    int lastLegIndex = -1;
    if ( legs.length > 0 ) {
      lastLegIndex = legs[legs.length-1].index;
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
    Comparator<LegTime> comparator = new LegTimeComparator();
    Arrays.sort(timeArray, comparator);
    for(int i = 1; i < timeArray.length; i++ ) {
      if (comparator.compare(timeArray[i-1], timeArray[i]) > 0) {
        throw new RuntimeException("bad comparison i=" + i);
      }
    }
    for( int i = 0; i < timeArray.length; i++ ) {
      LegTime legTime = timeArray[i];
      System.out.println( legTime.getLeg().index + " " + legTime.getTime1() + "->" + legTime.getTime2());
      legTime.previous = findPrevious(timeArray, i);
      if ( legTime.leg.index == lastLegIndex ) {
        legTime.last = true;
      }      
    }
    return timeArray;
  }
  
  public List<SequenceInstance> findInstances(LegTime[] legs) {
    List<SequenceInstance> instances = new ArrayList<SequenceInstance>();
    for( int i = 0; i < legs.length; i++ ) {
      if ( legs[i].isFirst() ) {
        for( int j = i; j < legs.length; j++ ) {
          if ( legs[j].isLast()) {
            SequenceInstance instance = new SequenceInstance(legs, i, j-i+1);
            instances.add(instance);
            break;
          }
        }
      }
    }
    return instances;
  }  
}
