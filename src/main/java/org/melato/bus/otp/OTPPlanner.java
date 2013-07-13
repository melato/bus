package org.melato.bus.otp;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.melato.bus.model.Route;
import org.melato.bus.model.RouteId;
import org.melato.bus.model.RouteManager;
import org.melato.bus.model.Stop;
import org.melato.bus.otp.OTP.Leg;
import org.melato.bus.otp.OTP.TransitLeg;
import org.melato.bus.plan.Plan;
import org.melato.bus.plan.PlanLeg;
import org.melato.bus.plan.Planner;
import org.melato.gps.Point2D;
import org.melato.log.Log;
import org.melato.update.Streams;

public class OTPPlanner implements Planner {
  RouteManager routeManager;
  String url = "http://192.168.2.9:8080/opentripplanner-api-webapp/ws/plan";
  
  public static class MismatchException extends Exception {
    private static final long serialVersionUID = 1L;

    public MismatchException(String message) {
      super(message);
    }
  }
  @Override
  public void setRouteManager(RouteManager routeManager) {
    this.routeManager = routeManager;
  }

  @Override
  public void setDepartureTime(Date date) {
  }

  @Override
  public void setArrivalTime(Date date) {
  }
  
  
  private Stop findStop(RouteId route, Stop[] stops, int offset, String code) throws MismatchException {
    for( int i = offset; i < stops.length; i++ ) {
      Stop stop = stops[i];
      if ( stop.getSymbol().equals(code)) {
        return stop;
      }
    }
    throw new MismatchException("cannot find stop: " + route + "(" + offset + ") -" + code + "-");
  }
  
  public Plan convertItinerary(OTP.Itinerary it, Point2D origin, Point2D destination) throws MismatchException {
    List<PlanLeg> legs = new ArrayList<PlanLeg>();
    for(Leg otpLeg: it.legs){
      if ( otpLeg instanceof TransitLeg) {
        TransitLeg transitLeg = (TransitLeg)otpLeg;
        RouteId routeId = new RouteId(transitLeg.routeId);
        Route route = routeManager.getRoute(routeId);
        if ( route == null) {
          throw new MismatchException( "cannot find route: " + transitLeg.routeId);
        }
        Stop[] stops = routeManager.getStops(routeId);
        Stop stop1 = findStop(routeId, stops, 0, transitLeg.from.id);
        Stop stop2 = findStop(routeId, stops, stop1.getIndex() + 1, transitLeg.to.id);        
        PlanLeg leg = new PlanLeg(route, stop1, stop2);
        legs.add(leg);
      }
    }
    return new Plan(origin, destination, legs.toArray(new PlanLeg[0]));
  }
  public Plan[] convertPlan(OTP.Plan otp, Point2D origin, Point2D destination) {
    List<Plan> plans = new ArrayList<Plan>();
    for(OTP.Itinerary it: otp.itineraries) {
      try {
        Plan plan = convertItinerary(it, origin, destination);
        plans.add(plan);
      } catch(MismatchException e) {
        System.out.println(e);
      }
    }
    return plans.toArray(new Plan[0]);
  }
  @Override
  public Plan[] plan(Point2D origin, Point2D destination) {
    OTPRequest request = new OTPRequest();
    request.setFromPlace(origin);
    request.setToPlace(destination);
    try {
      URL url = new URL(this.url + "?" + request.queryString());
      Log.info(url);
      String data = Streams.copyToString(url);
      Log.info("data.length: " + data.length());
      OTP.Plan otp = OTPParser.parse(data);
      return convertPlan(otp, origin, destination);
    } catch (MalformedURLException e) {
      throw new RuntimeException( e );
    } catch (IOException e) {
      throw new RuntimeException( e );
    }
  }

}
