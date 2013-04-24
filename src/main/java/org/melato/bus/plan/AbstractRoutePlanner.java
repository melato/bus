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

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.melato.bus.model.RStop;
import org.melato.bus.model.RouteId;
import org.melato.bus.model.RouteManager;
import org.melato.bus.model.Stop;
import org.melato.geometry.gpx.Path;
import org.melato.gps.Metric;
import org.melato.gps.Point2D;
import org.melato.util.AbstractCollector;



public abstract class AbstractRoutePlanner implements Planner {
  protected RouteManager routeManager;
  private Metric metric;
  private Map<RouteId,Stop[]> allStops = new HashMap<RouteId,Stop[]>();
  private float defaultSpeed = 20 * 1000f/3600f;
  protected float walkSpeed = 5 * 1000f/3600f;
  protected Date departureTime;
  protected Date arrivalTime;
  
  protected Metric getMetric() {
    if ( metric == null) {
      metric = routeManager.getMetric();
    }
    return metric;
  }
  public void setRouteManager(RouteManager routeManager) {
    this.routeManager = routeManager;
  }

  @Override
  public void setDepartureTime(Date date) {
    departureTime = date;
  }

  @Override
  public void setArrivalTime(Date date) {
    arrivalTime = date;
  }
  
  void fillDefaultTimes(Stop[] stops) {
    Path path = new Path(getMetric());
    path.setWaypoints(stops);
    for( int i = 0; i < stops.length; i++ ) {
      stops[i].setTime( 1000L * Math.round(path.getLength(i) / defaultSpeed)); 
    }    
  }
  // get the stops for a route, caching them in the process.
  public Stop[] getStops(RouteId routeId) {
    Stop[] stops = allStops.get(routeId);
    if ( stops == null) {
      stops = routeManager.getStops(routeId);
      if ( stops.length > 0 && stops[stops.length-1].getTime() == 0 ) {
        fillDefaultTimes(stops);        
      }
      allStops.put(routeId, stops);
    }
    return stops;
  }
  protected void findNearbyStops(RouteId routeId, Point2D point, float maxDistance, Collection<RStop> result) {
    Metric metric = getMetric();
    Stop[] stops = getStops(routeId);
    for( int i = 0; i < stops.length; i++ ) {
      Stop stop = stops[i];
      float distance = metric.distance(stop, point);
      if ( distance < maxDistance) {
        RStop p = new RStop(routeId, stop);
        p.setDistance(distance);
        result.add(p);
      }
    }
  }
  
  static class RouteFilter extends AbstractCollector<RStop> {
    Collection<RStop> result;
    Metric metric;
    Collection<RouteId> routes;
    
    private Point2D center;
    private float distance;
    
    public RouteFilter(Collection<RStop> result, Collection<RouteId> routes, Point2D center, float distance, Metric metric) {
      super();
      this.result = result;
      this.routes = routes;
      this.center = center;
      this.distance = distance;
      this.metric = metric;
    }

    @Override
    public boolean add(RStop p) {
      if ( ! routes.contains(p.getRouteId())) {
        return false;
      }
      float d = metric.distance(center, p.getStop());
      if ( d < distance ) {
        p.setDistance(d);
        result.add(p);
        size++;
        return true;
      }
      return false;
    }    
  }
  
  
  protected void findNearbyStops(Collection<RouteId> routes, Point2D point, float maxDistance, Collection<RStop> result) {
    Collection<RStop> filter = new RouteFilter(result, routes, point, maxDistance, routeManager.getMetric());
    routeManager.findNearbyStops(point, maxDistance, filter);
  }  
}
