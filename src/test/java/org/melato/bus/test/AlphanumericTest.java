package org.melato.bus.test;

import junit.framework.Assert;

import org.junit.Test;
import org.melato.bus.model.Alphanumeric;

public class AlphanumericTest {
  private void assertLess( String s1, String s2 ) {
    Assert.assertTrue( s1 + "<" + s2, Alphanumeric.compare( s1, s2 ) < 0);
    Assert.assertTrue( s2 + "<" + s1, Alphanumeric.compare( s2, s1 ) > 0);
  }
  public @Test void equality() {
    Assert.assertTrue( Alphanumeric.compare( "a", "a" ) == 0);
    Assert.assertTrue( Alphanumeric.compare( "12", "12" ) == 0);
    Assert.assertTrue( Alphanumeric.compare( "12b", "12b" ) == 0);
    Assert.assertTrue( Alphanumeric.compare( "a12b", "a12b" ) == 0);
    Assert.assertTrue( Alphanumeric.compare( "a12", "a12" ) == 0);    
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
