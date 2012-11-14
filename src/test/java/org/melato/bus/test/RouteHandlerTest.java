/*-------------------------------------------------------------------------
 * Copyright (c) 2012, Alex Athanasopoulos.  All Rights Reserved.
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

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.melato.bus.model.DaySchedule;
import org.melato.bus.model.Route;
import org.melato.bus.model.Schedule;
import org.melato.bus.model.xml.ScheduledRoute;
import org.melato.bus.model.xml.ScheduledRouteHandler;
import org.xml.sax.SAXException;

public class RouteHandlerTest {
  @Test public void xmlTest() throws IOException, SAXException {
      List<ScheduledRoute> list = ScheduledRouteHandler.parseScheduledRoutes( getClass().getResourceAsStream( "route.xml"));
      Assert.assertEquals(1, list.size());
      Route route = list.get(0).getRoute();
      Assert.assertEquals("304b", route.getRouteId().getName());
      Assert.assertEquals("304B", route.getLabel());
      Assert.assertEquals("Route Title", route.getTitle());
      Assert.assertEquals( 16, route.getColor());
      Schedule schedule = list.get(0).getSchedule();
      int[] times = null;
      
      // first test the low level DaySchedule
      DaySchedule[] schedules = schedule.getSchedules();
      Assert.assertEquals(2, schedules.length);
      Assert.assertEquals( 62, schedules[0].getDays() );
      times = schedules[0].getTimes();
      Assert.assertEquals( 2, times.length );
      Assert.assertEquals( 300, times[0] );
      Assert.assertEquals( 335, times[1] );
            
      times = schedule.getTimes(Calendar.TUESDAY);
      Assert.assertEquals( 2, times.length );
      Assert.assertEquals( 300, times[0] );
      Assert.assertEquals( 335, times[1] );

      times = schedule.getTimes(Calendar.SUNDAY);
      Assert.assertEquals( 1, times.length );
      Assert.assertEquals( 360, times[0] );
  }
}
