package org.melato.bus.model;



public class Alphanumeric {
  private static int findDigitsEnd(String s, int start) {
    int len = s.length();
    for( int i = start; i < len; i++ ) {
      if ( ! Character.isDigit(s.charAt(i))) {
        return i;        
      }
    }
    return len;
  }
  /**
   * Compare numbers as numbers and strings as strings.
   * Compare two strings, character by character.
   * If a digit is found in both strings at the same position,
   * compare all consecutive digits as a single number.
   * For example:  a9b < a10b (ordinary string comparison would return a10 < a9).
   * @param x
   * @param y
   * @return
   */
  public static int compare(String s1, String s2) {
    int len1 = s1.length();
    int len2 = s2.length();
    for( int i = 0; i < len1 && i < len2; i++ ) {
      char c1 = s1.charAt(i);
      char c2 = s2.charAt(i);
      if ( Character.isDigit(c1) && Character.isDigit(c2)) {
        int end1 = findDigitsEnd(s1, i + 1 );
        int end2 = findDigitsEnd(s2, i + 1 );
        if ( end1 != end2 )
          return end1 - end2;
        // numbers are of the same length.  Compare them digit by digit.
        for( int j = i; j < end1; j++ ) {
          c1 = s1.charAt(j);
          c2 = s2.charAt(j);
          if ( c1 != c2 ) {
            return c1 - c2;
          }
        }
        i = end1 - 1;
      } else {
        if ( c1 != c2 ) {
          return c1 - c2;
        }
      }      
    }
    return len1 - len2;
  }

}
