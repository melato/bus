package org.melato.bus.model;

/**
 * Contains basic information about a bus route in a certain direction,
 * including its schedule. 
 * @author Alex Athanasopoulos
 *
 */
public class Route implements Cloneable {
  /** The short bus line codename, usually numeric, e.g. "304" */
  String      name; // e.g. "304"
  /** The longer descriptive title of the bus line. */
  String      title; // e.g. "Γραμμή 304 ΣΤ. ΝΟΜΙΣΜΑΤΟΚΟΠΕΙΟ - ΑΡΤΕΜΙΣ (ΒΡΑΥΡΩΝΑ)"
  /** The direction of the route, "1" for outgoing, "2" for incoming.  */
  String      direction;
  /** The schedule of the route  */
  Schedule    schedule;
  /** A plain sequence of stop names for the route.  More extensive GPX information may be available elsewhere. */
  String[]    stops = new String[0];
  
  
  @Override
  public Route clone() {
    try {
      return (Route) super.clone();
    } catch( CloneNotSupportedException e ) {
      throw new RuntimeException(e);
    }
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
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
  public Schedule getSchedule() {
    return schedule;
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
}
