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
package org.melato.bus.test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import junit.framework.Assert;

import org.junit.Test;
import org.melato.bus.model.DaySchedule;
import org.melato.bus.model.ScheduleId;

public class ScheduleTest {
  @Test public void emptySchedule() {
    DaySchedule daySchedule = new DaySchedule(new int[0], ScheduleId.forWeek(DaySchedule.SUNDAY));
    Assert.assertEquals(-1, daySchedule.getClosestIndex(new Date()));
  }
  private void check3(int minute, int expectedIndex) {
    GregorianCalendar cal = new GregorianCalendar();
    cal.set(2013, Calendar.MARCH, 18 );
    cal.set(Calendar.MINUTE, minute);
    int[] times = new int[] { 60, 120, 180 };
    DaySchedule daySchedule = new DaySchedule(times, ScheduleId.forWeek(DaySchedule.MONDAY));
    Assert.assertEquals(expectedIndex, daySchedule.getClosestIndex(cal.getTime()));
  }
  @Test public void nearLow() {
    check3( 80, 0 );
  }
  @Test public void nearHigh() {
    check3( 100, 1 );
  }
  @Test public void nearFirst() {
    check3( 10, 0 );
  }
  @Test public void nearLast() {
    check3( 200, 2 );
  }
}
