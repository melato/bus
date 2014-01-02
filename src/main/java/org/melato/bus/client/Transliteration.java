package org.melato.bus.client;

/** Maps characters for transliteration. */
public class Transliteration {
  private String transliteration;
  
  /**
   * 
   * @param transliteration A string containing pairs of characters
   * The first character of the pair is mapped to the second.
   */
  public Transliteration(String transliteration) {
    super();
    this.transliteration = transliteration;
  }
  

  public Transliteration() {
    this("");
  }

  private char mapChar(char c) {
    int n = transliteration.length() / 2;
    for( int i = 0; i < n; i++ ) {
      if ( c == transliteration.charAt(i*2)) {
        return transliteration.charAt(i*2+1);
      }
    }
    return c;    
  }
  
  public String map(String text) {
    char[] chars = text.toCharArray();
    for(int i = 0; i < chars.length; i++ ) {
      char c = mapChar(chars[i]);
      chars[i] = c;
    }
    return new String(chars);
  }
}
