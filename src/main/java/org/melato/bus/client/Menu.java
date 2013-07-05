package org.melato.bus.client;

public class Menu {
  public String label;
  public String icon;
  public String type;
  public String target;
  public int startDate;
  public int endDate;
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
  public int getStartDate() {
    return startDate;
  }
  public void setStartDate(int startDate) {
    this.startDate = startDate;
  }
  public int getEndDate() {
    return endDate;
  }
  public void setEndDate(int endDate) {
    this.endDate = endDate;
  }
  public void setDate(int date) {
    this.startDate = date;
    this.endDate = date;
  }
  public boolean isActive(int dateId) {
    return (startDate == 0 || startDate <= dateId) && (startDate == 0 || dateId <= endDate);
  }
  @Override
  public String toString() {
    return label + " type=" + type + " target=" + target;
  }
}
