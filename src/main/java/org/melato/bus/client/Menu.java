package org.melato.bus.client;

public class Menu {
  public String name;
  public String label;
  public String icon;
  public String type;
  public String target;
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getLabel() {
    return label;
  }
  public void setLabel(String label) {
    this.label = label;
  }
  public String getIcon() {
    return icon;
  }
  public void setIcon(String icon) {
    this.icon = icon;
  }  
  public String getType() {
    return type;
  }
  public void setType(String type) {
    this.type = type;
  }
  public String getTarget() {
    return target;
  }
  public void setTarget(String target) {
    this.target = target;
  }
  @Override
  public String toString() {
    return name + " type=" + type + " target=" + target;
  }
}
