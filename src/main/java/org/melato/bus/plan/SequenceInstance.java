package org.melato.bus.plan;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import org.melato.bus.model.Schedule;

public class SequenceInstance implements Serializable {
  private static final long serialVersionUID = 1L;
  int startTime;
  int endTime;
  LegInstance[] legs;
  
  public static class LegInstance implements Serializable{
    private static final long serialVersionUID = 1L;
    private LegTime legTime;
    private LegTime previous;

    public LegInstance(LegTime legTime, LegTime previous) {
      super();
      this.legTime = legTime;
      this.previous = previous;
    }    
    

    @Override
    public String toString() {
      String s = legTime.toString();
      if ( previous != null) {
        s += " (wait " + Schedule.formatDuration(legTime.getTime1()-previous.getTime2()) + ")";
      }
      return s;
    }    
  }
  
  public SequenceInstance( List<LegTime> legTimes) {    
    legs = new LegInstance[legTimes.size()];
    LegTime previous = null;
    for( int i = 0; i < legs.length; i++ ) {
      LegTime leg = legTimes.get(i);
      legs[i] = new LegInstance(leg, previous);
      previous = leg;
    }
    startTime = legs[0].legTime.getTime1();
    endTime = legs[legs.length-1].legTime.getTime2();
  }
  
  public SequenceInstance( LegTime[] timeArray, int start, int length) {
    this(Arrays.asList(timeArray).subList(start, start + length));
  }
  
  public LegInstance[] getLegInstances() {
    return legs;
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder();
    buf.append( Schedule.formatTime(startTime/60));
    buf.append( " -> " );
    buf.append( Schedule.formatTime(endTime/60));
    buf.append( " (" );
    buf.append(Schedule.formatDuration(endTime-startTime));
    buf.append(")");
    return buf.toString();
  }
}
