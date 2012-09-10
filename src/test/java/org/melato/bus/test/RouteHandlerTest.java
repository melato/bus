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
