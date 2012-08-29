package org.melato.bus.model;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.melato.xml.XMLDelegator;
import org.melato.xml.XMLMappingHandler;
import org.melato.xml.XMLTag;
import org.xml.sax.SAXException;

/**
 * XML element handler for route
 * @author Alex Athanasopoulos
 */
public class ScheduleHandler extends XMLMappingHandler {
  TimeHandler timeHandler = new TimeHandler();
  int days;
  List<DaySchedule> schedules = new ArrayList<DaySchedule>();
  ScheduleHandler() {
    setHandler( RouteWriter.TIME, timeHandler );
  }

  public void clear() {
    timeHandler.clear();
    schedules.clear();
    days = 0;
  }
  
  public Schedule getSchedule() {
    return new Schedule( schedules.toArray( new DaySchedule[0] ) );
  }
  @Override
  public void start(XMLTag tag) throws SAXException {
    days = Integer.parseInt(tag.getRequiredAttribute(RouteWriter.DAYS));
    timeHandler.clear();
  }
  
  @Override
  public void end() throws SAXException {
    DaySchedule schedule = new DaySchedule(timeHandler.getTimes(), days);
    schedules.add(schedule);
  }  
}
