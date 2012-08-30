package org.melato.bus.test;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.melato.bus.model.RouteManager;
import org.melato.gpx.Point;
import org.melato.gpx.Waypoint;
import org.xml.sax.SAXException;

public class RouteManagerTest {
  @Test public void testNearbyStops() throws IOException, SAXException {
    URL url = getClass().getResource("data/");
    RouteManager routeManager = new RouteManager(url);
    Point target = new Point(37.98581f, 23.739164f);
    List<Waypoint> near = routeManager.findNearbyStops(target, 50);
    Assert.assertEquals(1, near.size());
  }
}
