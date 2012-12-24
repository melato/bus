package org.melato.bus.model;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateId {
  public static void setCalendar(int dateId, Calendar cal) {
    cal.set(Calendar.YEAR, getYear(dateId));
    cal.set(Calendar.MONTH, getMonth(dateId)-1);
    cal.set(Calendar.DAY_OF_MONTH, getDay(dateId));
  }
  public static int dateId(int year, int month, int day) {
    return year * 10000 + month * 100 + day;
    
  }
  public static int dateId(Calendar cal) {
    return dateId(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, cal.get(Calendar.DAY_OF_MONTH));
  }
  public static int dateId(Date date) {
    Calendar cal = new GregorianCalendar();
    cal.setTime(date);
    return dateId(cal);
  }
  public static int getYear(int dateId) {
    return dateId / 10000;
  }
  public static int getMonth(int dateId) {
    return ((dateId % 10000) / 100); 
  }
  public static int getDay(int dateId) {
    return dateId % 100;
  }
  public static String toString(int dateId) {
    return getYear(dateId) + "-" + getMonth(dateId) + "-" + getDay(dateId);
  }
}
