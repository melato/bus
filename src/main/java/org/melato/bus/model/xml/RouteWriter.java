package org.melato.bus.model.xml;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.melato.bus.model.Route;
import org.melato.xml.XMLWriter;

/**
 * Writes routes to XML
 * @author Alex Athanasopoulos
 */
public class RouteWriter {
  public static final String ROUTES = "routes";
  public static final String ROUTE = "route";
  public static final String NAME = "name";
  public static final String LABEL = "label";
  public static final String DIRECTION = "direction";
  public static final String TITLE = "title";
  public static final String DAYS = "days_bitmap";
  public static final String TIME = "time";
  public static final String VALUE = "value";

  public RouteWriter() {
  }

  public void begin(XMLWriter xml) {
    xml.printHeader();
    xml.tagOpen(RouteWriter.ROUTES);
    xml.println();
  }
  
  public void end(XMLWriter xml) {
    xml.tagEnd(RouteWriter.ROUTES);
    xml.println();
  }

  protected void routeBegin(Route route, XMLWriter xml ) {
    xml.tagOpen( ROUTE, false );
    xml.tagAttribute( NAME, route.getRouteId().getName() );
    xml.tagAttribute( LABEL, route.getLabel() );
    xml.tagAttribute( DIRECTION, route.getRouteId().getDirection() );
    xml.tagClose();
  }
  
  protected void routeBody(Route route, XMLWriter xml) {
    xml.println();
    xml.tagOpen( TITLE);
    xml.text(route.getTitle());
    xml.tagEnd(TITLE);
  }
  
  protected void routeEnd(Route route, XMLWriter xml ) {
    xml.println();
    xml.tagEnd(ROUTE);
    xml.println();
  }

  public void write(Route route, XMLWriter xml) {
    routeBegin(route, xml);
    routeBody(route, xml);
    routeEnd(route, xml);    
  }
  
  public void writeRoutes(List<Route> routes, File file) throws IOException {
    XMLWriter xml = new XMLWriter(file);
    try {
      begin(xml);
      for( Route route: routes ) {
        write(route, xml);
      }
      end(xml);
    } finally {
      xml.close();
    }
  }
  
}
