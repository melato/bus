package org.melato.bus.model.xml;

import org.melato.bus.model.DaySchedule;
import org.melato.bus.model.Route;
import org.melato.bus.model.Schedule;
import org.melato.xml.XMLWriter;


/**
 * Writes routes to XML
 * @author Alex Athanasopoulos
 */
public class ScheduledRouteWriter extends RouteWriter {
  public static final String SCHEDULE = "schedule";
  private void writeSchedules(Schedule schedule, XMLWriter xml) {
    if ( schedule == null ) {
      return;
    }
    for( DaySchedule daySchedule: schedule.getSchedules() ) {
      xml.tagOpen( SCHEDULE, false );
      xml.tagAttribute(DAYS, String.valueOf(daySchedule.getDays()));
      xml.tagClose();
      xml.println();
      for( int time: daySchedule.getTimes() ) {
        xml.tagOpen( TIME, false );
        xml.tagAttribute( VALUE, Schedule.formatTime(time));
        xml.tagClose(true);
        xml.println();
      }
      xml.tagEnd( SCHEDULE );
      xml.println();
    }
  }
  public void write(ScheduledRoute sr, XMLWriter xml) {
    Route route = sr.getRoute();
    routeBegin(route, xml);
    routeBody(route, xml);
    writeSchedules(sr.getSchedule(), xml);    
    routeEnd(route, xml);
  }
}
