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

import junit.framework.Assert;

import org.junit.Test;
import org.melato.bus.model.AlphanumericComparator;

public class AlphanumericTest {
  private void assertLess( String s1, String s2 ) {
    Assert.assertTrue( s1 + "<" + s2, AlphanumericComparator.INSTANCE.compare( s1, s2 ) < 0);
    Assert.assertTrue( s2 + "<" + s1, AlphanumericComparator.INSTANCE.compare( s2, s1 ) > 0);
  }
  public @Test void equality() {
    Assert.assertTrue( AlphanumericComparator.INSTANCE.compare( "a", "a" ) == 0);
    Assert.assertTrue( AlphanumericComparator.INSTANCE.compare( "12", "12" ) == 0);
    Assert.assertTrue( AlphanumericComparator.INSTANCE.compare( "12b", "12b" ) == 0);
    Assert.assertTrue( AlphanumericComparator.INSTANCE.compare( "a12b", "a12b" ) == 0);
    Assert.assertTrue( AlphanumericComparator.INSTANCE.compare( "a12", "a12" ) == 0);    
  }
  public @Test void strings() {
    assertLess( "a", "b" );
    assertLess( "a", "aa" );
  }
  public @Test void numbers() {
    assertLess( "a2", "a10" );
    assertLess( "a2", "a2b" );
    assertLess( "2", "10" );
    assertLess( "a2a35", "a2a100" );
  }

}
