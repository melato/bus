package org.melato.bus.client;

public class HelpItem {
  String name;
  String title;
  String text;
  int   node;
    
  public String getTitle() {
    return title;
  }
  public void setTitle(String title) {
    this.title = title;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getText() {
    return text;
  }
  public void setText(String text) {
    this.text = text;
  }
  public int getNode() {
    return node;
  }
  public void setNode(int node) {
    this.node = node;
  }
}
