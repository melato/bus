package org.melato.bus.test;

import junit.framework.Assert;

import org.junit.Test;
import org.melato.bus.client.Formatting;

public class FormattingTest {
  @Test
  public void bearingTest() {
    Assert.assertEquals(2f, Formatting.normalizeBearing(2f), 0.1f);
    Assert.assertEquals(-2f, Formatting.normalizeBearing(-2f), 0.1f);
    Assert.assertEquals(-2f, Formatting.normalizeBearing(358f), 0.1f);
    Assert.assertEquals(-179f, Formatting.normalizeBearing(181f), 0.1f);
    Assert.assertEquals(1f, Formatting.normalizeBearing(361f), 0.1f);
    Assert.assertEquals(-1f, Formatting.normalizeBearing(-361f), 0.1f);
  }

}
