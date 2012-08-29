package org.melato.bus.model;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


/** Maintains departure information for one route and one day.
 * May apply to several days of the week if they all have the same schedule every day. */
public class DaySchedule {
  public static final int SUNDAY = 1;
  public static final int SATURDAY = 1 << 6;
  /** times are stored as minutes from midnight. */
  private int[] times;
  private int   days;  // days is a bitmap, bit 0 = sunday, bit 6 = saturday
  
  public int[] getTimes() {
    return times;
  }

  public int getDays() {
    return days;
  }

  public DaySchedule(int[] times, int days) {
    super();
    Arrays.sort(times);
    this.times = times;
    this.days = days;
  }
  
  public static DaySchedule findSchedule(DaySchedule[] schedules, int dayOfWeek) {
    int bitmap = 1 << (dayOfWeek-Calendar.SUNDAY);
    for( DaySchedule schedule: schedules ) {
      if ( (schedule.days & bitmap) != 0 ) {
        return schedule;
      }
    }
    return null;
  }
  public static DaySchedule findSchedule(DaySchedule[] schedules, Date date) {
    Calendar cal = new GregorianCalendar();
    cal.setTime(date);
    return findSchedule(schedules, cal.get(Calendar.DAY_OF_WEEK));
  }
}
