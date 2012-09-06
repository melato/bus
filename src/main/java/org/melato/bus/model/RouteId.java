package org.melato.bus.model;

/**
 * A RouteId is a persistent id for the route.
 * It is used as the database id in the XmlRouteModel, and as a cross-database route id.
 * @author Alex Athanasopoulos
 *
 */
public class RouteId implements Id {
  private static final long serialVersionUID = 1L;
  /** The internal name, e.g. 301b */
  private String  name;
  /** The direction of the route, "1" for outgoing, "2" for incoming.  */
  private String direction;
    
  public RouteId(String name, String direction) {
    super();
    this.name = name;
    this.direction = direction;
  }
  public RouteId(String stringId) {
    String[] fields = stringId.split("-");
    name = fields[0];
    direction = fields[1];    
  }
  @Override
  public String toString() {
    return name + "-" + direction;
  }
  
  public String getName() {
    return name;
  }
  public String getDirection() {
    return direction;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  public void setDirection(String direction) {
    this.direction = direction;
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
    RouteId other = (RouteId) obj;
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
