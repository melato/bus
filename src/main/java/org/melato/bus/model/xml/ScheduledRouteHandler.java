package org.melato.bus.model.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.melato.bus.model.Route;
import org.melato.bus.model.Schedule;
import org.melato.xml.XMLStringHandler;
import org.xml.sax.SAXException;

/**
 * XML element handler for route
 * @author Alex Athanasopoulos
 */
public class ScheduledRouteHandler extends RouteHandler {
  private List<ScheduledRoute> scheduledRoutes = new ArrayList<ScheduledRoute>();
  private ScheduleHandler scheduleHandler = new ScheduleHandler();
  private XMLStringHandler commentHandler = new XMLStringHandler();
  
  public ScheduledRouteHandler() {
    super();
    setHandler( ScheduledRouteWriter.SCHEDULE, scheduleHandler );
    setHandler( ScheduledRouteWriter.SCHEDULE_COMMENT, commentHandler );
  }

  protected void addRoute(Route route) {
    Schedule schedule = scheduleHandler.getSchedule();
    schedule.setComment(commentHandler.getText());
    ScheduledRoute sr = new ScheduledRoute(route, schedule);
    scheduledRoutes.add(sr);
  }
  
  @Override
  public void end() throws SAXException {
    super.end();
    scheduleHandler.clear();
  }
  
  public static List<ScheduledRoute> parseScheduledRoutes(InputStream in) throws IOException, SAXException {
    ScheduledRouteHandler ScheduledRouteHandler = new ScheduledRouteHandler();
    ScheduledRouteHandler.parse(in);
    return ScheduledRouteHandler.getScheduledRoutes();
  }
  public static List<ScheduledRoute> parseScheduledRoutes(File file) throws IOException {
    try {
      return parseScheduledRoutes(new FileInputStream(file));
    } catch(SAXException e ) {
      throw new IOException(e);
    }
  }
  public List<ScheduledRoute> getScheduledRoutes() {
    return scheduledRoutes;
  }
  
}
