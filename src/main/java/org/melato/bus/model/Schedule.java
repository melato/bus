package org.melato.bus.model;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


/** A schedule maintains departure information for one route and for all days of the week. */
public class Schedule {
  private DaySchedule[] schedules;

  static DecimalFormat d2Format = new DecimalFormat("00");
  
  public static String formatTime(int time) {
    return d2Format.format(time/60) + ":" + d2Format.format(time%60);
  }
  
  public static int parseTime(String time) {
    int p = time.indexOf(':');
    if ( p < 0 )
      throw new IllegalArgumentException( "Invalid time: " + time );
    return Integer.parseInt(time.substring(0,p)) * 60 + Integer.parseInt(time.substring(p+1));
  }
    
  /** Create an empty schedule. */
  public Schedule() {
  }

  public Schedule(DaySchedule[] schedules) {
    super();
    this.schedules = schedules;
  }

  public DaySchedule[] getSchedules() {
    return schedules;
  }
  
  public DaySchedule getSchedule(Date date) {
    return DaySchedule.findSchedule(schedules, date);    
  }
  /** Get the schedule times for a given day of the week. */
  public int[] getTimes( Date date ) {
    DaySchedule schedule = getSchedule(date);
    if ( schedule == null ) {
      return new int[0];
    }
    return schedule.getTimes();
  }
  
  /** Get the schedule times for a given day of the week. */
  public int[] getTimes( int dayOfWeek ) {
    DaySchedule schedule = DaySchedule.findSchedule(schedules, dayOfWeek);
    if ( schedule == null ) {
      return new int[0];
    }
    return schedule.getTimes();
  }
  
  /** Get the time in minutes since midnight */
  public static int getTime( Date date ) {
    Calendar cal = new GregorianCalendar();
    cal.setTime(date);
    int hour = cal.get(Calendar.HOUR_OF_DAY);
    int minute = cal.get(Calendar.MINUTE);
    return hour * 60 + minute;
  }
}
