package org.melato.bus.plan;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.melato.bus.client.Formatting;
import org.melato.bus.model.RStop;
import org.melato.bus.model.Schedule;
import org.melato.gps.GlobalDistance;

/** A sequence scheduled at a particular time */
public class SequenceInstance implements Serializable {
  private static final long serialVersionUID = 1L;
  int startTime;
  int endTime;
  List<SequenceInstanceLeg> legs;
  
  public static interface SequenceInstanceLeg {
    
  }
  public static class LegInstance implements SequenceInstanceLeg, Serializable {
    private static final long serialVersionUID = 1L;
    private LegTime legTime;
    private int     waitSeconds;

    public LegInstance(LegTime legTime, LegTime previous) {
      super();
      this.legTime = legTime;
      if ( previous != null) {
        waitSeconds = legTime.getTime1()-previous.getTime2();
      } else {
        waitSeconds = -1;
      }
    }    
    
    public RStop getRStop() {
      return legTime.leg.getRStop1();
    }    

    @Override
    public String toString() {
      String s = legTime.toString();
      if ( waitSeconds >= 0 ) {
        s += " (wait " + Schedule.formatDuration(waitSeconds) + ")";
      }
      return s;
    }    
  }
  public static class WalkInstance implements SequenceInstanceLeg, Serializable {
    private static final long serialVersionUID = 1L;
    private float distance;
    public WalkInstance(LegTime leg1, LegTime leg2) {
      distance = new GlobalDistance().distance(leg1.getLeg().getStop2(), leg2.getLeg().getStop1());
    }
    @Override
    public String toString() {
      return "Walk " + Formatting.straightDistance(distance) + " " + Walk.distanceDuration(distance);
    }    
  }
  
  
  public SequenceInstance( List<LegTime> legTimes) {
    legs = new ArrayList<SequenceInstanceLeg>(); 
    LegTime previous = null;
    int size = legTimes.size();
    for( int i = 0; i < size; i++ ) {
      LegTime leg = legTimes.get(i);
      if ( previous != null) {
        legs.add(new WalkInstance(previous, leg));
      }
      legs.add(new LegInstance(leg, previous));
      previous = leg;
    }
    startTime = legTimes.get(0).getTime1();
    endTime = legTimes.get(size-1).getTime2();
  }
  
  public SequenceInstance( LegTime[] timeArray, int start, int length) {
    this(Arrays.asList(timeArray).subList(start, start + length));
  }
    
  /** Start time in seconds. */
  public int getStartTime() {
    return startTime;
  }

  /** Start time in seconds. */
  public int getEndTime() {
    return endTime;
  }

  public List<SequenceInstanceLeg> getLegInstances() {
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
