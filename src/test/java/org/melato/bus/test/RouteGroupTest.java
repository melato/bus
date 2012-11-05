/*-------------------------------------------------------------------------
 * Copyright (c) 2012, Alex Athanasopoulos.  All Rights Reserved.
 * alex@melato.org
 *-------------------------------------------------------------------------
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *-------------------------------------------------------------------------
 */
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
