package org.melato.bus.test;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.melato.bus.model.Route;
import org.melato.bus.model.RouteId;
import org.melato.bus.model.RouteManager;
import org.melato.bus.model.xml.XmlRouteStorage;
import org.melato.gpx.GPX;
import org.melato.gpx.Point;
import org.melato.gpx.Waypoint;
import org.xml.sax.SAXException;

public class RouteManagerTest {
  @Test public void testNearbyStops() throws IOException, SAXException {
    URL url = getClass().getResource("data/");
    RouteManager routeManager = new RouteManager(new XmlRouteStorage(url));
    Point target = new Point(37.98581f, 23.739164f);
    List<Waypoint> near = routeManager.findNearbyStops(target, 50);
    Assert.assertEquals(1, near.size());
  }
  @Test public void testGPXRoute() throws Exception {
    URL url = getClass().getResource("data/");
    RouteManager routeManager = new RouteManager(new XmlRouteStorage(url));
    List<Route> routes = routeManager.getRoutes();
    Assert.assertEquals(2, routes.size());
    GPX gpx = routeManager.loadGPX(new RouteId("021", "1"));
    Assert.assertEquals(1, gpx.getRoutes().size());
    List<Waypoint> waypoints = gpx.getRoutes().get(0).path.getWaypoints();
    Assert.assertTrue(waypoints.size() > 2 );
  }
}
