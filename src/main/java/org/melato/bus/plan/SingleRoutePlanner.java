/*-------------------------------------------------------------------------
 * Copyright (c) 2012,2013, Alex Athanasopoulos.  All Rights Reserved.
 * alex@melato.org
 *-------------------------------------------------------------------------
 * This file is part of Athens Next Bus
 *
 * Athens Next Bus is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Athens Next Bus is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Athens Next Bus.  If not, see <http://www.gnu.org/licenses/>.
 *-------------------------------------------------------------------------
 */
package org.melato.bus.plan;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.melato.bus.model.DaySchedule;
import org.melato.bus.model.RStop;
import org.melato.bus.model.Route;
import org.melato.bus.model.RouteId;
import org.melato.bus.model.Schedule;
import org.melato.bus.plan.StopPair.RouteComparator;
import org.melato.gps.Point2D;
import org.melato.log.Log;


public class SingleRoutePlanner extends AbstractRoutePlanner {
  
  
  List<RStop> filterRoutes(Iterable<RStop> stops, Set<RouteId> allowed) {
    List<RStop> result = new ArrayList<RStop>();
    for( RStop stop: stops ) {
      if ( allowed.contains(stop.getRouteId())) {
        result.add(stop);
      }
    }
    return result;
  }
  Set<RouteId> routeSet(Iterable<RStop> stops) {
    Set<RouteId> routes = new HashSet<RouteId>();
    for( RStop stop: stops ) {
      routes.add(stop.getRouteId());
    }
    return routes;    
  }    
  /**
   * remove pairs with the same route, keeping only the one with the shortest time.
   * @param pairs
   * @return
   */
  int removeDuplicates(StopPair[] pairs) {
    int n = 0;
    RouteId routeId = null;
    float bestTime = 0;
    for( int i = 0; i < pairs.length; i++ ) {
      StopPair pair = pairs[i];
      if ( ! pair.getStop1().getRouteId().equals(routeId)) {
        pairs[n++] = pair;
        routeId = pair.getStop1().getRouteId();
        bestTime = pair.getTime();
      } else {
        float time = pair.getTime();
        if (time < bestTime) {
          bestTime = time;
          pairs[n-1] = pair;
        }
      }
    }
    for( int i = n; i < pairs.length; i++ ) {
      pairs[i] = null;
    }
    return n;
  }

  /** in seconds */
  public int[] getNearestTimes(Date date, PlanLeg leg) {
    int[] times = null;
    date = new Date(date.getTime() - leg.getStop1().getTime());
    int time = Schedule.getTime(date);
    DaySchedule daySchedule = routeManager.getDaySchedule(leg.getRoute(), date);
    if ( daySchedule != null ) {
      int[] dayTimes = daySchedule.getTimes();
      int index = daySchedule.getNextIndex(time);
      if ( index < 0 ) {
        times = new int[0];
      } else if ( index == dayTimes.length-1) {
        times = new int[] { dayTimes[dayTimes.length-1] };
      } else {  
        times = new int[] {dayTimes[index], dayTimes[index+1]}; 
      }
      for(int i = 0; i < times.length; i++ ) {
        times[i] = times[i] * 60 + (int) (leg.getStop1().getTime() / 1000L);
      }
    }
    if ( times == null)
      times = new int[0];
    return times;
  }  
    
  private void setDepartureTime(Plan plan, Date startDate) {
    int startTime = Schedule.getSeconds(startDate);
    plan.setDepartureTime(startTime);
    int time = startTime;
    PlanLeg[] legs = plan.getLegs();
    for(PlanLeg leg: legs ) {
      time += leg.getDistanceBefore() / walkSpeed;
      Date date = new Date(startDate.getTime() + (long) ((time-startTime) * 1000));
      int[] times = getNearestTimes(date, leg);
      if (times == null || times.length == 0) {
        plan.setDuration(Float.NaN);
        return;
      }
      time = times[0];
      leg.setDepartureTime(time);
      leg.setNearestTimes(times);
      time += leg.getDuration();
    }
    time += plan.getLastWalkDistance() / walkSpeed;
    plan.setArrivalTime(time);
    plan.setDuration(time-startTime);
  }
  
  void updatePlan(Plan plan) {
    plan.setDistances(getMetric());
    if ( departureTime != null) {
      setDepartureTime(plan, departureTime);
    } else if ( arrivalTime != null) {
      
    } else {
      setDepartureTime(plan, new Date());
    }
  }
  
  Plan createPlan(Point2D origin, Point2D destination, StopPair pair) {
    Route route = routeManager.getRoute(pair.getStop1().getRouteId());
    PlanLeg leg = new PlanLeg(route, pair.getStop1().getStop(), pair.getStop2().getStop());
    leg.setDistanceBefore(pair.getStop1().getDistance());
    Plan plan = new Plan(origin, destination, new PlanLeg[] {leg});
    return plan;
  }
  
  @Override
  public Plan[] plan(Point2D origin, Point2D destination) {
    float distanceAB = getMetric().distance(origin,destination);
    Log.info("plan origin=" + origin + " dest=" + destination + " distance=" + (int) distanceAB);
    List<RStop> nearby1 = new ArrayList<RStop>();
    routeManager.findNearbyStops(origin, distanceAB, nearby1);
    Log.info("nearby1: " + nearby1.size());
    Set<RouteId> routes1 = routeSet(nearby1);
    List<RStop> nearby2 = new ArrayList<RStop>();
    findNearbyStops(routes1, destination, distanceAB, nearby2);
    Log.info("nearby2: " + nearby2.size());
    Set<RouteId> routes2 = routeSet(nearby2);
    nearby1 = filterRoutes(nearby1, routes2);
    Log.info("filtered nearby1: " + nearby1.size());
    List<StopPair> pairs = new ArrayList<StopPair>();
    for( RStop stop1: nearby1) {
      for( RStop stop2: nearby2) {
        if ( stop1.isBefore(stop2)) {
          float distance = stop1.getDistance() + stop2.getDistance();
          if ( distance < distanceAB ) {
            StopPair pair = new StopPair(stop1, stop2);
            float time = distance / walkSpeed + (stop2.getStop().getTime() - stop1.getStop().getTime())/1000f;
            pair.setTime(time);
            pairs.add(pair);
          }
        }
      }
    }
    StopPair[] array = pairs.toArray(new StopPair[0]);
    Arrays.sort( array, new RouteComparator() );
    int size = removeDuplicates(array);
    List<Plan> list = new ArrayList<Plan>();
    Plan walk = new Plan(origin, destination);
    updatePlan(walk);
    list.add(walk);
    for( int i = 0; i < size; i++ ) {
      Plan plan = createPlan(origin, destination, array[i]);
      updatePlan(plan);
      if ( plan.getDuration() < walk.getDuration()) {
        list.add(plan);
      }
    }
    Plan[] plans = list.toArray(new Plan[0]);
    Arrays.sort(plans);
    return plans;
  }
  
}
