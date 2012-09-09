package org.melato.bus.client;

import java.util.AbstractList;
import java.util.Date;

import org.melato.bus.model.DaySchedule;
import org.melato.bus.model.Schedule;
import org.melato.log.Log;

public class TimeOfDayList extends AbstractList<TimeOfDay> {
  private int[] times;
  private Date  currentTime;

  public TimeOfDayList(int[] times, Date currentTime) {
    this.times = times;
    this.currentTime = currentTime;
    Log.info( "TimeOfDayList times.length=" + times.length );
  }
  public TimeOfDayList(Schedule schedule, Date currentTime) {
    this(schedule.getTimes(currentTime), currentTime);
  }
  public TimeOfDayList(DaySchedule schedule, Date currentTime) {
    this(schedule.getTimes(), currentTime);;
  }
  @Override
  public TimeOfDay get(int location) {
    return new TimeOfDay(times[location]);
  }

  @Override
  public int size() {
    return times.length;
  }
  
  public int getDefaultPosition() {
    int time = Schedule.getTime(currentTime);
    for( int i = 1; i < times.length; i++ ) {
      if ( times[i] >= time )
        return i - 1;
    }
    return times.length - 1;
  }
  
  
}


