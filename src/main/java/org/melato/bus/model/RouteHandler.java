package org.melato.bus.model;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.melato.xml.XMLDelegator;
import org.melato.xml.XMLMappingHandler;
import org.melato.xml.XMLStringHandler;
import org.melato.xml.XMLTag;
import org.xml.sax.SAXException;

/**
 * XML element handler for route
 * @author Alex Athanasopoulos
 */
public class RouteHandler extends XMLMappingHandler {
  List<Route> routes = new ArrayList<Route>();
  XMLStringHandler titleHandler = new XMLStringHandler();
  ScheduleHandler scheduleHandler = new ScheduleHandler();
  StopHandler stopHandler = new StopHandler();
  Route route;


  
  public RouteHandler() {
    setHandler( RouteWriter.TITLE, titleHandler );
    setHandler( RouteWriter.SCHEDULE, scheduleHandler );
    //setHandler( RouteWriter.STOPS, stopHandler );
  }
  
  @Override
  public void start(XMLTag tag) throws SAXException {
    route = new Route();
    route.setName(tag.getRequiredAttribute(RouteWriter.NAME));
    route.setDirection(tag.getRequiredAttribute(RouteWriter.DIRECTION));
    super.start(tag);
  }
  @Override
  public void end() throws SAXException {
    super.end();
    route.setTitle(titleHandler.getText());
    route.setSchedule(scheduleHandler.getSchedule());
    scheduleHandler.clear();
    stopHandler.clear();
    routes.add(route);
  }
  public static List<Route> parse(InputStream in) throws IOException, SAXException {
    XMLMappingHandler root = new XMLMappingHandler();
    RouteHandler routeHandler = new RouteHandler();
    XMLMappingHandler routesHandler = new XMLMappingHandler();
    root.setHandler( RouteWriter.ROUTES, routesHandler);
    routesHandler.setHandler(RouteWriter.ROUTE, routeHandler);    
    XMLDelegator.parse(root, in);
    return routeHandler.getRoutes();
  }
  public List<Route> getRoutes() {
    return routes;
  }
  
}
