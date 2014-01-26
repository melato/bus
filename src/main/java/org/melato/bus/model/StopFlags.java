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


public class StopFlags {
  public static int seatFlag(Boolean hasSeat) {
    if ( hasSeat == null ) {
      return 0;
    }
    return Stop.FLAG_KNOWN_SEAT | (hasSeat ? Stop.FLAG_SEAT : 0);
  }
  public static int coverFlag(Boolean hasCover) {
    if ( hasCover == null ) {
      return 0;
    }
    return Stop.FLAG_KNOWN_COVER | (hasCover ? Stop.FLAG_COVER : 0);
  }
  public static Boolean getSeat(int flags) {
    if ( (flags & Stop.FLAG_KNOWN_SEAT) != 0) {
      return (flags & Stop.FLAG_SEAT) != 0 ? Boolean.TRUE : Boolean.FALSE;
    } else {
      return null;
    }
  }
  public static Boolean getCover(int flags) {
    if ( (flags & Stop.FLAG_KNOWN_COVER) != 0) {
      return (flags & Stop.FLAG_COVER) != 0 ? Boolean.TRUE : Boolean.FALSE;
    } else {
      return null;
    }
  }
}
