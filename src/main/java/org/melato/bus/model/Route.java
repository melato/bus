package org.melato.bus.model;

import org.melato.gpx.GPX;
import org.melato.log.Log;

/**
 * Contains basic information about a bus route in a certain direction,
 * including its schedule. 
 * @author Alex Athanasopoulos
 *
 */
public class Route implements Cloneable, Comparable<Route> {
  private Id  id;
  private RouteId routeId;
  
  /** The label that the user sees, e.g. "301B"
   * The label is usually name in uppercase.
   * */
  private String label;
  
  /** The longer descriptive title of the bus line. */
  private String      title; // e.g. "Γραμμή 304 ΣΤ. ΝΟΜΙΣΜΑΤΟΚΟΠΕΙΟ - ΑΡΤΕΜΙΣ (ΒΡΑΥΡΩΝΑ)"
  /** The schedule of the route  */
  private Schedule    schedule;
  /** A plain sequence of stop names for the route.  More extensive GPX information may be available elsewhere. */
  private String[]    stops = new String[0];
  
  RouteManager routeManager;  
  
  
  public Route() {
    super();
  }

  public Route(Id id) {
    super();
    this.id = id;
  }

  @Override
  public Route clone() {
    try {
      return (Route) super.clone();
    } catch( CloneNotSupportedException e ) {
      throw new RuntimeException(e);
    }
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
    return routeId.getDirection();
  }
  private RouteManager getRouteManager() {
    if ( routeManager == null ) {
      throw new RuntimeException( "No RouteManager" );
    }
    return routeManager;
  }
  
  public Schedule getSchedule() {
    if ( schedule == null ) {
      schedule = getRouteManager().loadSchedule(getRouteId());
    }
    Log.info("Route.getSchedule: " + schedule );
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

  public String getFullTitle() {
    return getLabel() + " " + getTitle();
  }
  
  @Override
  public String toString() {
    return getFullTitle();
  }

  @Override
  public int compareTo(Route r) {
    int d = AlphanumericComparator.INSTANCE.compare(label, r.label);
    if ( d != 0 )
      return d;
    return AlphanumericComparator.INSTANCE.compare(getDirection(), r.getDirection());
  }
  
  /** This is an internal id, used internally only by the route database.
   * */
  public Id getId() {
    return id;
  }

  public void setId(Id id) {
    this.id = id;
  }
  
  /** This is an external route id, used for storing in caches, etc.
   * */
  public RouteId getRouteId() {
    return routeId;
  }

  public void setRouteId(RouteId routeId) {
    this.routeId = routeId;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((routeId == null) ? 0 : routeId.hashCode());
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
    if (routeId == null) {
      if (other.routeId != null)
        return false;
    } else if (!routeId.equals(other.routeId))
      return false;
    return true;
  }

}
