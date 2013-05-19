package org.melato.bus.client;

import org.melato.bus.model.Schedule.DateScheduleFactory;
import org.melato.bus.model.Schedule.ScheduleFactory;
import org.melato.bus.model.Schedule.ScheduleIdScheduleFactory;
import org.melato.bus.model.ScheduleId;
import org.melato.util.DateId;

/**
 * Computes user-friendly names for schedules.
 * This class is abstract so that it can be implemented using different localization mechanisms.
 * @author Alex Athanasopoulos
 *
 */
public abstract class ScheduleNames {
  private static int getFirstBit(int bitmap ) {
    if ( bitmap == 0 )
      return -1;
    for( int i = 0; i < 32; i++ ) {
      int bit = 1 << i;
      if ( (bitmap & bit) != 0 ) {
        return i;
      }
    }
    return -1;
  }
  private static int getLastBit(int bitmap ) {
    if ( bitmap == 0 )
      return -1;
    for( int i = 31; i >= 0; i-- ) {
      int bit = 1 << i;
      if ( (bitmap & bit) != 0 ) {
        return i;
      }
    }
    return -1;
  }
  private static boolean isContiguous( int bitmap, int first, int last ) {
    for( int i = first; i <= last; i++ ) {
      int bit = 1 << i;
      if ( (bitmap & bit) == 0 ) {
        return false;
      }
    }
    return true;    
  }

  /**
   * Get the name of a day.
   * @param day The day index, 0 = Sunday
   * @return
   */
  public abstract String getDayName(int day);
  public abstract String getAllDaysName();
  public abstract String getTodayName();
  
  public String getDaysName(int days) {    
    int first = getFirstBit(days);
    int last = getLastBit(days);
    if ( first == last ) {
      return getDayName(first);      
    }
    if ( days == 127 ) {
      return getAllDaysName();
    }
    if ( isContiguous(days, first, last)) {
      return getDayName(first) + "-" + getDayName(last);
    }
    StringBuilder buf = new StringBuilder();
    for( int i = first; i <= last; i++ ) {
      int bit = 1 << i;
      if ( (days & bit) != 0 ) { 
        if ( i > first ) {
          buf.append(",");
        }
        buf.append(getDayName(i));
      }
    }
    return buf.toString();
  }
  
  public String getScheduleName(ScheduleId scheduleId) {
    int days = scheduleId.getDays();
    if ( days == 0 ) {
      return DateId.toString(scheduleId.getDateId());
    }
    return getDaysName(days);
  }

  public String getScheduleName(ScheduleFactory schedule) {
    if ( schedule instanceof ScheduleIdScheduleFactory ) {
      return getScheduleName(((ScheduleIdScheduleFactory)schedule).getScheduleId());      
    }
    if ( schedule instanceof DateScheduleFactory ) {
      return getTodayName();      
    }
    return schedule.toString();
  }
}
