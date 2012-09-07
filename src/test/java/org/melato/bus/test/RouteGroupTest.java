package org.melato.bus.test;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.melato.bus.model.RouteGroup;
import org.melato.bus.model.RouteManager;
import org.melato.bus.model.xml.XmlRouteStorage;
import org.xml.sax.SAXException;

public class RouteGroupTest {
  @Test public void testNearbyStops() throws IOException, SAXException {
    URL url = getClass().getResource("data/");
    RouteManager routeManager = new RouteManager(new XmlRouteStorage(url));
    List<RouteGroup> groups = RouteGroup.group(routeManager.getRoutes());
    Assert.assertEquals(3, groups.size());
    RouteGroup group = groups.get(2);
    Assert.assertEquals(2, group.getRoutes().length);
    Assert.assertEquals("099", group.getRoutes()[0].getLabel());
  }
}
