package org.melato.bus.client;

import java.util.List;

public interface MenuStorage {
  List<Menu> loadMenus();
  byte[] loadImage(String name);
}
