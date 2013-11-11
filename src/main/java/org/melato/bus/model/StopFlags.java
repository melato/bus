package org.melato.bus.model;


public class StopFlags {
  public static int seatFlag(Boolean hasSeat) {
    if ( hasSeat == null ) {
      return 0;
    }
    return Stop.FLAG_KNOWN_SEAT | (hasSeat ? Stop.FLAG_SEAT : 0);
  }
  public static int coverFlag(Boolean hasCover) {
    if ( hasCover == null ) {
      return 0;
    }
    return Stop.FLAG_KNOWN_COVER | (hasCover ? Stop.FLAG_COVER : 0);
  }
  public static Boolean getSeat(int flags) {
    if ( (flags & Stop.FLAG_KNOWN_SEAT) != 0) {
      return (flags & Stop.FLAG_SEAT) != 0 ? Boolean.TRUE : Boolean.FALSE;
    } else {
      return null;
    }
  }
  public static Boolean getCover(int flags) {
    if ( (flags & Stop.FLAG_KNOWN_COVER) != 0) {
      return (flags & Stop.FLAG_COVER) != 0 ? Boolean.TRUE : Boolean.FALSE;
    } else {
      return null;
    }
  }
}
