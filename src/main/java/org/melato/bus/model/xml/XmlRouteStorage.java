
package org.melato.bus.model.xml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.List;

import org.melato.bus.model.AbstractRouteStorage;
import org.melato.bus.model.Id;
import org.melato.bus.model.NearbyFilter;
import org.melato.bus.model.Route;
import org.melato.bus.model.RouteHandler;
import org.melato.bus.model.RouteId;
import org.melato.gpx.GPX;
import org.melato.gpx.GPXParser;
import org.melato.gpx.Point;
import org.melato.gpx.Waypoint;
import org.xml.sax.SAXException;

public class XmlRouteStorage extends AbstractRouteStorage {
  public static final String ROUTES_FILE = "routes.xml";
  public static final String STOPS_FILE = "stops.gpx";
  public static final String ROUTES_DIR = "routes";
  public static final String GPX_DIR = "gpx";
  private URL dataUrl;
  
  public XmlRouteStorage(File dataDir) {
    super();
     try {
      this.dataUrl = dataDir.toURI().toURL();
    } catch (MalformedURLException e) {
      throw new RuntimeException( e );
    }   
  }


  public XmlRouteStorage(URL dataUrl) {
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
  
  public List<Route> loadRoutes() {
    try {
      URL url = makeUrl( ROUTES_FILE );
      List<Route> routes = RouteHandler.parse(url.openStream());
      return routes;
    } catch( IOException e ) {
      throw new RuntimeException(e);
    } catch( SAXException e ) {
      throw new RuntimeException(e);
    }
  }

  public Route loadRoute(RouteId routeId) {
    try {
      URL url = makeUrl( ROUTES_DIR, routeId + ".xml" );
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


  @Override
  public GPX loadGPX(RouteId routeId) {
    try {
      URL url = makeUrl( GPX_DIR, routeId + ".gpx" );
      GPXParser parser = new GPXParser();
      return parser.parse(url.openStream());
    } catch( FileNotFoundException e ) {
      System.err.println( e );
      return null;
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
  
  @Override
  public void iterateNearbyStops(Point point, float distance,
      Collection<Waypoint> collector) {
    iterateAllStops(new NearbyFilter(point, distance, collector));
  }
}
