/*-------------------------------------------------------------------------
 * Copyright (c) 2012,2013, Alex Athanasopoulos.  All Rights Reserved.
 * alex@melato.org
 *-------------------------------------------------------------------------
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *-------------------------------------------------------------------------
 */
package org.melato.bus.plan;

import java.util.Comparator;


public class LegTimeComparator implements Comparator<LegTime> {

  private static int compare(int a, int b ) {
    if ( a < b )
      return -1;
    if ( a > b )
      return 1;
    return 0;
  }
  @Override
  public int compare(LegTime o1, LegTime o2) {
    int time1 = 0; 
    int time2 = 0;
    if ( o1.leg.index == o2.leg.index ) {
      time1 = o1.time * 60 + (int) (o1.leg.getStop1().time / 1000L); 
      time2 = o2.time * 60 + (int) (o2.leg.getStop1().time / 1000L);
      return compare(time1, time2);
    }
    if ( o1.leg.index > o2.leg.index ) {
      LegTime t = o1;
      o1 = o2;
      o2 = t;
    }
    time1 = o1.time * 60 + (int) (o1.leg.getStop2().time / 1000L); 
    time2 = o2.time * 60 + (int) (o2.leg.getStop1().time / 1000L);
    return compare(time1, time2);
  }
}
