package org.melato.bus.test;

import java.util.Calendar;

import org.junit.Assert;
import org.junit.Test;

public class CalendarTest {
  @Test public void testSunday() {
    Assert.assertEquals(1, Calendar.SUNDAY);
  }
  @Test public void testSaturday() {
    Assert.assertEquals(7, Calendar.SATURDAY);
  }

}
