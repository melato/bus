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
    this.title = routes[0].getLabel() + " " + routes[0].getTitle();
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
    String label = null;
    List<Route> group = new ArrayList<Route>();
    for(Route route: routes ) {
      if (label == null ) {
        group.add(route);
        label = route.getLabel();
      } else if ( label.equals(route.getLabel())) {
        group.add(route);
      } else {
        groups.add(new RouteGroup(group));
        group.clear();
        group.add(route);
        label = route.getLabel();
      }
    }
    groups.add(new RouteGroup(group));    
    return groups;    
  }
  
}
