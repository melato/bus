package org.melato.bus.plan;

import org.melato.bus.model.Schedule;
import org.melato.bus.otp.OTP;

public class OTPLegAdapter implements LegAdapter {
  private OTP.Leg leg;
  private OTP.TransitLeg transit;
  private OTP.WalkLeg walk;

  
  public OTPLegAdapter(OTP.Leg leg) {
    super();
    this.leg = leg;
    if ( leg instanceof OTP.TransitLeg ) {
      transit = (OTP.TransitLeg) leg;
    }
    if ( leg instanceof OTP.WalkLeg ) {
      walk = (OTP.WalkLeg) leg;
    }
  }

  @Override
  public String getLabel() {
    return transit.label;
  }

  @Override
  public String getFromName() {
    return transit.from.name;
  }

  @Override
  public String getToName() {
    return transit.to.name;
  }

  @Override
  public int getStartTime() {
    return Schedule.getSeconds(leg.startTime);
  }

  @Override
  public int getEndTime() {
    return Schedule.getSeconds(leg.endTime);
  }

  @Override
  public int getDuration() {
    return leg.getDuration();
  }

  @Override
  public int getDiffTime() {
    return transit != null ? transit.diffTime : -1;
  }

  @Override
  public float getDistance() {
    return leg.distance;
  }

  @Override
  public boolean isWalk() {
    return walk != null;
  }
}

