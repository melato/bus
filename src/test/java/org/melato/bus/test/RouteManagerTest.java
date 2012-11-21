/*-------------------------------------------------------------------------
 * Copyright (c) 2012, Alex Athanasopoulos.  All Rights Reserved.
 * alex@melato.org
 *-------------------------------------------------------------------------
 * This file is part of Athens Next Bus
 *
 * Athens Next Bus is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Athens Next Bus is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Athens Next Bus.  If not, see <http://www.gnu.org/licenses/>.
 *-------------------------------------------------------------------------
 */
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
import org.melato.gps.Point2D;
import org.melato.gpx.GPX;
import org.melato.gpx.Waypoint;
import org.xml.sax.SAXException;

public class RouteManagerTest {
  @Test public void testNearbyStops() throws IOException, SAXException {
    URL url = getClass().getResource("data/");
    RouteManager routeManager = new RouteManager(new XmlRouteStorage(url));
    Point2D target = new Point2D(37.98581f, 23.739164f);
    List<Waypoint> near = routeManager.findNearbyStops(target, 50);
    Assert.assertEquals(1, near.size());
  }
  @Test public void testGPXRoute() throws Exception {
    URL url = getClass().getResource("data/");
    RouteManager routeManager = new RouteManager(new XmlRouteStorage(url));
    List<Route> routes = routeManager.getRoutes();
    Assert.assertEquals(4, routes.size());
    GPX gpx = routeManager.loadGPX(new RouteId("021", "1"));
    Assert.assertEquals(1, gpx.getRoutes().size());
    List<Waypoint> waypoints = gpx.getRoutes().get(0).path.getWaypoints();
    Assert.assertTrue(waypoints.size() > 2 );
  }
}
