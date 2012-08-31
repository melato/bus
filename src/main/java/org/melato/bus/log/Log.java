package org.melato.bus.log;

/**
 * A simple debugging log facility that can be used in different environments (e.g. Java SE, Android).
 * This is not intended to replace other logging facilities (there are too many already),
 * but to provide a very simple debugging tool.
 * Use for debugging.  When you're done, remove all Log calls.
 * @author Alex Athanasopoulos
 *
 */
public class Log {
  private static Logger logger;
  
  public static void setLogger(Logger logger) {
    Log.logger = logger;
  }
  public static void info( String message ) {
    if ( logger != null ) {
      logger.log(message);
    }
    
  }
}
