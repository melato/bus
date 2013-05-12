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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Serialization {
  public static Object read(Class<?> clazz, File file) {
    System.out.println( "read " + file);
    try {
      ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
      try {
        Object obj = in.readObject();
        if ( ! clazz.isInstance(obj)) {
          obj = null;
        }
        return obj;
      } finally {
        in.close();
      }
    } catch( Exception e ) {
      System.out.println(e);
      return null;
    }
  }
  
  public static void write(Object object, File file) throws IOException {
    ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
    try {
      out.writeObject(object);
    } finally {
      out.close();
    }
  }

}
