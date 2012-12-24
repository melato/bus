package org.melato.bus.model;

/** Used to alter the schedule for holidays or other special days. */
public class ScheduleException implements Comparable<ScheduleException> {
  private int dateId;
  private DaySchedule daySchedule;
    
  public ScheduleException(int dateId, DaySchedule daySchedule) {
    super();
    this.dateId = dateId;
    this.daySchedule = daySchedule;
  }
  public int getDateId() {
    return dateId;
  }
  public DaySchedule getDaySchedule() {
    return daySchedule;
  }
  @Override
  public int compareTo(ScheduleException o) {
    return dateId - o.dateId;
  }  
  
  
}
