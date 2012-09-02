package org.melato.bus.model;

import org.melato.gpx.GPX;

/**
 * Contains basic information about a bus route in a certain direction,
 * including its schedule. 
 * @author Alex Athanasopoulos
 *
 */
public class Route implements Cloneable, Comparable<Route> {
  /** The internal name, e.g. 301b */
  private String      name; // e.g. "304"
  
  /** The label that the user sees, e.g. "301B"
   * The label is usually name in uppercase.
   * */
  private String label;
  
  /** The longer descriptive title of the bus line. */
  private String      title; // e.g. "Γραμμή 304 ΣΤ. ΝΟΜΙΣΜΑΤΟΚΟΠΕΙΟ - ΑΡΤΕΜΙΣ (ΒΡΑΥΡΩΝΑ)"
  /** The direction of the route, "1" for outgoing, "2" for incoming.  */
  private String      direction;
  /** The schedule of the route  */
  Schedule    schedule;
  /** A plain sequence of stop names for the route.  More extensive GPX information may be available elsewhere. */
  private String[]    stops = new String[0];
  
  RouteManager routeManager;  
  
  @Override
  public Route clone() {
    try {
      return (Route) super.clone();
    } catch( CloneNotSupportedException e ) {
      throw new RuntimeException(e);
    }
  }
  
  public static String qualifiedName(String name, String direction ) {
    return name + "-" + direction; 
  }
  public String qualifiedName() {
    return qualifiedName(getName(), getDirection());
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  
  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public String getTitle() {
    return title;
  }
  public void setTitle(String title) {
    this.title = title;
  }
  public String getDirection() {
    return direction;
  }
  public void setDirection(String direction) {
    this.direction = direction;
  }
  private RouteManager getRouteManager() {
    if ( routeManager == null ) {
      throw new RuntimeException( "No RouteManager" );
    }
    return routeManager;
  }
  public Schedule getSchedule() {
    if ( schedule == null ) {
      Route route = getRouteManager().loadRoute(this);
      if ( route != null ) {
        this.schedule = route.schedule;
      } else {
        this.schedule = new Schedule();
      }
    }
    return schedule;
  }
  
  public GPX loadGPX() {
    GPX gpx = getRouteManager().loadGPX(this);
    return gpx;
  }
  
  public void setSchedule(Schedule schedule) {
    this.schedule = schedule;
  }
  public String[] getStops() {
    return stops;
  }
  public void setStops(String[] stops) {
    this.stops = stops;
  }

  @Override
  public String toString() {
    return qualifiedName() + " " + title;
  }

  @Override
  public int compareTo(Route r) {
    int d = AlphanumericComparator.INSTANCE.compare(name, r.name);
    if ( d != 0 )
      return d;
    return AlphanumericComparator.INSTANCE.compare(direction, r.direction);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((direction == null) ? 0 : direction.hashCode());
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Route other = (Route) obj;
    if (direction == null) {
      if (other.direction != null)
        return false;
    } else if (!direction.equals(other.direction))
      return false;
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    return true;
  }
  
  
  
}
