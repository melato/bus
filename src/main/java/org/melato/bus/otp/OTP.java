package org.melato.bus.otp;

import java.util.Date;

public class OTP {
  public static class Plan {
    public Itinerary[] itineraries;
  }
  public static class Itinerary {
    public Leg[] legs;
  }
  public static class Leg {
    public Date startTime;
    public Date endTime;
    public float distance;
    public int duration;
  }
  public static class WalkLeg extends Leg {    
  }
  public static class TransitLeg extends Leg { 
    public String routeId;
    public String fromStopId;
    public String toStopId;
  }
}
