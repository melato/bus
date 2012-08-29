package org.melato.bus.model;

import java.util.List;

public class IntArrays {
  public static int[] toArray(List<Integer> list) {
    int[] array = new int[list.size()];
    for( int i = 0; i < array.length; i++ ) {
      array[i] = list.get(i);
    }
    return array;
  }
}
