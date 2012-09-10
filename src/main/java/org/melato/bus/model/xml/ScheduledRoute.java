package org.melato.bus.model.xml;

import org.melato.bus.model.Route;
import org.melato.bus.model.Schedule;

/** An object that holds a route and its schedule. */
public class ScheduledRoute {
  private Route route;
  private Schedule schedule;
  public ScheduledRoute(Route route, Schedule schedule) {
    super();
    this.route = route;
    this.schedule = schedule;
  }
  public Route getRoute() {
    return route;
  }
  public Schedule getSchedule() {
    return schedule;
  }
}
