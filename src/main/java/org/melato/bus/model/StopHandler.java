package org.melato.bus.model;

import java.util.ArrayList;
import java.util.List;

import org.melato.xml.XMLMappingHandler;
import org.melato.xml.XMLTag;
import org.xml.sax.SAXException;

/**
 * XML element handler for stop
 * @author Alex Athanasopoulos
 */
public class StopHandler extends XMLMappingHandler {
  List<String> stops = new ArrayList<String>();
  
  void clear() {
    stops.clear();
  }
  @Override
  public void start(XMLTag tag) throws SAXException {
    stops.add( tag.getRequiredAttribute(RouteWriter.TITLE));
  }

  String[] getStops() {
    return stops.toArray(new String[0]);
  }
}
