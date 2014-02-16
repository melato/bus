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

import java.io.Serializable;

/** Represents the endpoints parameters in space and time for a plan.
 * Any of the parameters may be missing. 
 * <ul>
 * <li>Origin
 * <li>Destination
 * <li>Time
 * <li>Arrive At
 * @author alex
 *
 */
public class PlanEndpoints implements Serializable {
  private static final long serialVersionUID = 1L;
  public NamedPoint origin;
  public NamedPoint destination;
  /** minutes from midnight */
  public Integer time; 
  public boolean arriveAt;
  
  public String getName() {
    if ( origin == null && destination == null) {
      return "";
    }
    if ( origin == null ) {
      return destination.toString();
    }
    if ( destination == null) {
      return origin.toString();
    }
    return origin + " -> " + destination;
  }

  @Override
  public String toString() {
    return "Endpoints: " + origin + " -> " + destination + " time=" + time + " arriveAt=" + arriveAt; 
  }
  
}
