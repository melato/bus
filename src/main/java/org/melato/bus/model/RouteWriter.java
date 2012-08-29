package org.melato.bus.model;

import org.melato.xml.XMLWriter;

/**
 * Writes routes to XML
 * @author Alex Athanasopoulos
 */
public class RouteWriter {
  public static final String ROUTES = "routes";
  public static final String ROUTE = "route";
  public static final String NAME = "name";
  public static final String DIRECTION = "direction";
  public static final String TITLE = "title";
  public static final String SCHEDULE = "schedule";
  public static final String DAYS = "days_bitmap";
  public static final String TIME = "time";
  public static final String VALUE = "value";

  public static final String STOPS = "stops";
  public static final String STOP = "stop";
  
  private boolean includeStops;
  private boolean includeSchedule = true;
  
  public RouteWriter() {
  }

  private void writeStops(Route route, XMLWriter xml) {
    if ( route.getStops() == null )
      return;
    xml.tagOpen( STOPS);
    xml.println();
    for( String stop: route.getStops() ) {
      xml.tagOpen( STOP, false );
      xml.tagAttribute( TITLE, stop);
      xml.tagClose(true);
      xml.println();
    }
    xml.tagEnd( STOPS );
    xml.println();
  }
  
  private void writeSchedules(Route route, XMLWriter xml) {
    Schedule schedule = route.getSchedule();
    if ( schedule == null ) {
      return;
    }
    for( DaySchedule daySchedule: schedule.getSchedules() ) {
      xml.tagOpen( SCHEDULE, false );
      xml.tagAttribute(DAYS, String.valueOf(daySchedule.getDays()));
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
  
  public void write(Route route, XMLWriter xml) {
    xml.tagOpen( ROUTE, false );
    xml.tagAttribute( NAME, route.getName() );
    xml.tagAttribute( DIRECTION, route.getDirection() );
    xml.tagClose();
    xml.println();
    xml.tagOpen( TITLE);
    xml.text(route.getTitle());
    xml.tagEnd(TITLE);
    xml.println();
    if ( includeSchedule ) {
      writeSchedules(route, xml);
    }
    if ( includeStops ) {
      writeStops(route, xml);
    }
    xml.tagEnd(ROUTE);
    xml.println();
  }

  public void setIncludeStops(boolean includeStops) {
    this.includeStops = includeStops;
  }

  public void setIncludeSchedule(boolean includeSchedule) {
    this.includeSchedule = includeSchedule;
  }
  
}
