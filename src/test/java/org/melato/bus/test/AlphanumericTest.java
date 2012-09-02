package org.melato.bus.test;

import java.util.Comparator;

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
