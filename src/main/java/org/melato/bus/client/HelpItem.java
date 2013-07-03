package org.melato.bus.client;

/** A help item.  Used to display a page of help information in an android activity. */
public class HelpItem {
  private String name;
  private String title;
  private String text;
  private String node;
    
  /** The title of the item.  Should be short enough for an activity title.  */ 
  public String getTitle() {
    return title;
  }
  public void setTitle(String title) {
    this.title = title;
  }
  /** Internal optional mnemonic id for the item. */
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  /** The text content (body) of the item.  */
  public String getText() {
    return text;
  }
  public void setText(String text) {
    this.text = text;
  }
  /** Internal persistent id for the item. */
  public String getNode() {
    return node;
  }
  public void setNode(String node) {
    this.node = node;
  }
}
