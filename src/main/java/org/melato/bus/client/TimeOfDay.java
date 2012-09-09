package org.melato.bus.client;

import org.melato.bus.model.Schedule;

public class TimeOfDay {
  private int time;
  
  
  public TimeOfDay(int minutes) {
    super();
    this.time = minutes;
  }

  @Override
  public String toString() {
    return Schedule.formatTime(time);
  }    
}

