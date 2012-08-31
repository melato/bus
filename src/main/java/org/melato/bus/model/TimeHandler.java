package org.melato.bus.model;

import java.util.ArrayList;
import java.util.List;

import org.melato.util.IntArrays;
import org.melato.xml.XMLMappingHandler;
import org.melato.xml.XMLTag;
import org.xml.sax.SAXException;

/**
 * XML element handler for route
 * @author Alex Athanasopoulos
 */
public class TimeHandler extends XMLMappingHandler {
  List<Integer> times = new ArrayList<Integer>();
  
  void clear() {
    times.clear();
  }
  @Override
  public void start(XMLTag tag) throws SAXException {
    int time = Schedule.parseTime(tag.getRequiredAttribute(RouteWriter.VALUE));
    times.add(time);
  }
  int[] getTimes() {
    return IntArrays.toArray(times);
  }
}
