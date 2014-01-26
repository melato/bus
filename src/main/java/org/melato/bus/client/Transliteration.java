/*-------------------------------------------------------------------------
 * Copyright (c) 2012,2013, Alex Athanasopoulos.  All Rights Reserved.
 * alex@melato.org
 *-------------------------------------------------------------------------
 * This file is part of Athens Next Bus
 *
 * Athens Next Bus is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Athens Next Bus is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Athens Next Bus.  If not, see <http://www.gnu.org/licenses/>.
 *-------------------------------------------------------------------------
 */
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
