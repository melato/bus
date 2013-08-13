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
package org.melato.bus.model;

import java.util.ArrayList;
import java.util.List;

/** A route group is a set of related routes, usually a route and its return route. */
public class RouteGroup {
  private String title;
  private Route[] routes;
  public String getTitle() {
    return title;
  }
  public Route[] getRoutes() {
    return routes;
  }
  private void setRoutes(Route[] routes) {
    this.routes = routes;
    this.title = routes[0].getFullTitle();
    this.title = title.toUpperCase();  // just in case.  make sure search is case insensitive.
  }
  public RouteGroup(Route[] routes) {
    setRoutes(routes);
  }

  public RouteGroup(List<Route> list) {
    setRoutes(list.toArray(new Route[0]));
  }
    
  @Override
  public String toString() {
    return title;
  }

  /*
  private void add(Route route) {
    Route[] array = new Route[routes.length+1];
    for( int i = 0; i < routes.length; i++ ) {
      array[i] = routes[i];
    }
    array[routes.length]= route;
    routes = array;
  }
  */
  
  /**
   * Create a list of route groups from a list of sorted routes.
   * @param routes
   * @return
   */
  public static List<RouteGroup> group(List<Route> routes) {
    List<RouteGroup> groups = new ArrayList<RouteGroup>();
    String groupName = null;
    List<Route> group = new ArrayList<Route>();
    for(Route route: routes ) {
      String name = route.getRouteId().getName();
      if (groupName == null ) {
        group.add(route);
        groupName = name;
      } else if ( groupName.equals(name)) {
        group.add(route);
      } else {
        groups.add(new RouteGroup(group));
        group.clear();
        group.add(route);
        groupName = name;
      }
    }
    groups.add(new RouteGroup(group));    
    return groups;    
  }
  
}
