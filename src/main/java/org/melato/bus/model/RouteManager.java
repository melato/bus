package org.melato.bus.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.melato.gpx.GPX;
import org.melato.gpx.GPXParser;
import org.xml.sax.SAXException;


/**
 * Provides read-access to routes.
 * For use on Java SE or Android
 * @author Alex Athanasopoulos
 *
 */
public class RouteManager {
  public static final String ROUTES_FILE = "routes.xml";
  public static final String STOPS_FILE = "stops.gpx";
  public static final String ROUTES_DIR = "routes";
  public static final String GPX_DIR = "gpx";
  
  private File  dataDir;
  private List<Route> routes;
  
  public RouteManager(File dataDir) {
    super();
    this.dataDir = dataDir;
  }

  public List<Route> getRoutes() {
    if ( routes == null ) {
      File file = new File(dataDir, ROUTES_FILE);
      routes = Collections.emptyList();
      try {
        routes = RouteHandler.parse(new FileInputStream(file));
        for( Route route: routes ) {
          route.routeManager = this;
          route.schedule = null;          
        }
      } catch( IOException e ) {
        throw new RuntimeException(e);
      } catch( SAXException e ) {
        throw new RuntimeException(e);
      }
    }
    return routes;
  }

  Route loadRoute(Route route) {
    File file = new File(dataDir, ROUTES_DIR);
    file = new File(file, route.qualifiedName() + ".xml" );
    //System.out.println( "loading " + file );
    try {
      List<Route> routes = RouteHandler.parse(new FileInputStream(file));
      if ( routes.isEmpty() ) {
        throw new RuntimeException( "Cannot load " + file );
      }
      // assume there is only one route in the file.  Return the first one.
      return routes.get(0);
    } catch( IOException e ) {
      throw new RuntimeException(e);
    } catch( SAXException e ) {
      throw new RuntimeException(e);
    }
  }

  GPX loadGPX(Route route) {
    File file = new File(dataDir, GPX_DIR);
    file = new File(file, route.qualifiedName() + ".gpx" );
    try {
      GPXParser parser = new GPXParser();
      return parser.parse(file);
    } catch( IOException e ) {
      throw new RuntimeException(e);
    }
  }
  
}
