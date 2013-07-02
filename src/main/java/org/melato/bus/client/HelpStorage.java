package org.melato.bus.client;

public interface HelpStorage {
  HelpItem loadHelp(String name);
  HelpItem loadHelp(int node);
}
