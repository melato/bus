package org.melato.bus.plan;

import java.io.Serializable;

import org.melato.bus.model.Schedule;

public class SequenceInstance implements Serializable {
  private static final long serialVersionUID = 1L;
  int startTime;
  int endTime;
  LegTime[] legs;
  
  String debug() {
    /*
    StringBuilder buf = new StringBuilder();
    for(LegTime leg: legs) {
      buf.append( leg);
      buf.append( " ");
    }
    buf.append( "(");
    buf.append( legs.length + ")");
    return buf.toString();
    */
    return legs[0].getRoute().getLabel() + " " + legs.length;
  }
  public SequenceInstance( LegTime[] timeArray, int start, int length) {
    legs = new LegTime[length];
    System.arraycopy(timeArray,  start, legs, 0, length);
    startTime = this.legs[0].getTime1();
    endTime = legs[legs.length-1].getTime2();
    System.out.println( debug());
  }

  @Override
  public String toString() {
    /*
    StringBuilder buf = new StringBuilder();
    buf.append( Schedule.formatTime(startTime));
    buf.append( " -> " );
    buf.append( Schedule.formatTime(endTime));
    buf.append( " (" );
    buf.append(Schedule.formatDuration((endTime-startTime)*60));
    buf.append(")");
    return buf.toString();
    */
    return debug();
  }
}
