package org.melato.bus.plan;

public interface LegAdapter {
  String getLabel();    
  String getFromName();
  String getToName();
  int getStartTime();
  int getEndTime();
  int getDuration();
  
  int getDiffTime();
  float getDistance();
  boolean isWalk();
}