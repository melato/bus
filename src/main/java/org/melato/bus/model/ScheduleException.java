package org.melato.bus.model;

/** Used to alter the schedule for holidays or other special days. */
public class ScheduleException implements Comparable<ScheduleException> {
  private int dateId;
  private DaySchedule daySchedule;
  /** Used for internal identification purposes, e.g. when saving the exception to XML, etc. */
  private String id;
    
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
  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }
  @Override
  public int compareTo(ScheduleException o) {
    return dateId - o.dateId;
  }  
  
  
}
