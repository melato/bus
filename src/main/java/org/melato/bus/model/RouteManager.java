package org.melato.bus.model;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.melato.gpx.GPX;
import org.melato.gpx.GPXParser;
import org.melato.gpx.Point;
import org.melato.gpx.Waypoint;
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
  
  private URL dataUrl;
  private List<Route> routes;
  
  public RouteManager(File dataDir) {
    super();
     try {
      this.dataUrl = dataDir.toURI().toURL();
    } catch (MalformedURLException e) {
      throw new RuntimeException( e );
    }   
  }

  public RouteManager(URL dataUrl) {
    super();
    this.dataUrl = dataUrl;
  }

  private URL makeUrl(String file ) throws MalformedURLException {
    URL url = new URL(dataUrl, file );
    return url;    
  }
  
  private URL makeUrl(String dir, String file ) throws MalformedURLException {
    URL url = new URL(dataUrl, dir + "/" + file );      
    return url;    
  }
  
  public List<Route> getRoutes() {
    if ( routes == null ) {
      routes = Collections.emptyList();
      try {
        URL url = makeUrl( ROUTES_FILE );
        routes = RouteHandler.parse(url.openStream());
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

  public Route getRoute( String qualifiedName ) {
    for( Route route: getRoutes() ) {
      if (qualifiedName.equals(route.qualifiedName()))
          return route;
    }
    return null;    
  }

  public Route loadRoute( String qualifiedName ) {
    try {
      URL url = makeUrl( ROUTES_DIR, qualifiedName + ".xml" );
      //System.out.println( "loading " + url );
      List<Route> routes = RouteHandler.parse(url.openStream());
      if ( routes.isEmpty() ) {
        throw new RuntimeException( "Cannot load " + url );
      }
      // assume there is only one route in the file.  Return the first one.
      return routes.get(0);
    } catch( IOException e ) {
      throw new RuntimeException(e);
    } catch( SAXException e ) {
      throw new RuntimeException(e);
    }
  }
    
  Route loadRoute(Route route) {
    return loadRoute(route.qualifiedName());
  }

  GPX loadGPX(Route route) {
    try {
      URL url = makeUrl( GPX_DIR, route.qualifiedName() + ".gpx" );
      GPXParser parser = new GPXParser();
      return parser.parse(url.openStream());
    } catch( IOException e ) {
      throw new RuntimeException(e);
    }
  }

  public GPX loadAllStops() {
    try {
      URL url = makeUrl( STOPS_FILE );
      GPXParser parser = new GPXParser();
      return parser.parse(url.openStream());
    } catch( IOException e ) {
      throw new RuntimeException(e);
    }
  }
  
  public void iterateAllStops(Collection<Waypoint> collector) {
    try {
      URL url = makeUrl( STOPS_FILE );
      GPXParser parser = new GPXParser();
      parser.parseWaypoints(url.openStream(), collector);
    } catch( IOException e ) {
      throw new RuntimeException(e);
    }
  }
  
  public List<Waypoint> findNearbyStops(Point point, float distance) {
    List<Waypoint> result = new ArrayList<Waypoint>();
    iterateAllStops(new NearbyFilter(point, distance, result));
    return result;
  }
}
