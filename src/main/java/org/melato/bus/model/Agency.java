package org.melato.bus.model;

public class Agency {
  private String name;
  private String label;
  private String url;
  private String routeUrl;
  private byte[] icon;
  
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
  public String getUrl() {
    return url;
  }
  public void setUrl(String url) {
    this.url = url;
  }  
  public String getRouteUrl() {
    return routeUrl;
  }
  public void setRouteUrl(String routeUrl) {
    this.routeUrl = routeUrl;
  }
  public byte[] getIcon() {
    return icon;
  }
  public void setIcon(byte[] icon) {
    this.icon = icon;
  }
  @Override
  public String toString() {
    return label;
  }
}
