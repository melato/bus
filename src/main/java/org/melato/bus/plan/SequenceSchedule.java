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
import org.melato.util.AbstractListGrouper;
import org.melato.util.DateId;

public class SequenceSchedule {
  private int dateId;
  private Level[] levels;
  private List<SequenceInstance> instances;

  static class Level {
    int level;
    Leg[] legs;
    LegTime[] legTimes;
    int[] times;
    public Level(Leg[] legs) {
      super();
      this.level = legs[0].index;
      this.legs = legs;
    } 
    void compute(Date date, RouteManager routeManager) {
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
      legTimes = timeList.toArray(new LegTime[0]);
      Arrays.sort(legTimes);
      times = new int[legTimes.length];
      for( int i = 0; i < times.length; i++ ) {
        times[i] = legTimes[i].getTime1();
      }
    }
    
    int findTimeIndex(int time) {
      int pos = Arrays.binarySearch(times, time);
      if ( pos >= 0 )
        return pos;
      pos = -(pos+1);
      if ( pos < times.length ) {
        return pos;
      }
      return -1;
    }
  }
  
  static class LevelGrouper extends AbstractListGrouper<Leg> {
    private List<Level> levels = new ArrayList<Level>();
    public Level[] getLevels() {
      return levels.toArray(new Level[0]);
    }
    @Override
    protected boolean inSameGroup(Leg item, Leg nextItem) {
      return item.index == nextItem.index;
    }

    @Override
    protected void addGroup(List<Leg> group) {
      levels.add(new Level(group.toArray(new Leg[0])));
    }
    
  }
  public void setDateId(int dateId) {
    this.dateId = dateId;
  }

  public SequenceSchedule(Sequence sequence, RouteManager routeManager) {
    LevelGrouper grouper = new LevelGrouper();
    grouper.group(sequence.getLegs());
    levels = grouper.getLevels();
    Date date = null;
    if ( dateId != 0 ) {
      date = DateId.getDate(dateId);
    } else {
      date = new Date();
    }
    for(Level level: levels ) {
      level.compute(date, routeManager);
    }
    instances = createInstances(levels);
  }

  public List<SequenceInstance> getInstances() {
    return instances;
  }

  public int getTimePosition(int minutes) {
    int size = instances.size();
    int seconds = minutes * 60;
    for( int i = 0; i < size; i++ ) {
      SequenceInstance instance = instances.get(i);
      if ( seconds < instance.getStartTime() ) {
        return i;
      }
    }
    return -1;
  }  
  
  public int getTimePosition(Date date) {
    return getTimePosition(Schedule.getTime(date));
  }  
  
  private List<SequenceInstance> createInstances(Level[] levels) {
    List<SequenceInstance> instances = new ArrayList<SequenceInstance>();
    if ( levels.length == 0 )
      return instances;
    Level firstLevel = levels[0];
    LegTime[] legTimes = new LegTime[levels.length];
    for(LegTime firstLeg: firstLevel.legTimes) {
      //System.out.println( "first leg: " + firstLeg);
      legTimes[0] = firstLeg;
      int time = firstLeg.getTime2();
      boolean complete = true;
      for( int i = 1; i < levels.length; i++ ) {
        int timeIndex = levels[i].findTimeIndex(time);
        if ( timeIndex >= 0 ) {
          LegTime leg = levels[i].legTimes[timeIndex];
          legTimes[i] = leg;
          time = leg.getTime2();
          //System.out.println( leg);
        } else {
          complete = false;
          break;
        }
      }      
      if ( complete ) {
        SequenceInstance instance = new SequenceInstance(Arrays.asList(legTimes));
        instances.add(instance);        
      } else {
        break;
      }
    }
    return instances;
  }  
}
