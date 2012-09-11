package org.melato.bus.client;


/** Specifies information about a downloadable file. */
public class UpdateFile {
  public String name;
  public String url;
  public String expires;
  public String version;
  public int    size;
  
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getUrl() {
    return url;
  }
  public void setUrl(String url) {
    this.url = url;
  }
  public String getExpires() {
    return expires;
  }
  public void setExpires(String expires) {
    this.expires = expires;
  }
  public String getVersion() {
    return version;
  }
  public void setVersion(String version) {
    this.version = version;
  }  
  public int getSize() {
    return size;
  }
  public void setSize(int size) {
    this.size = size;
  }
  @Override
  public String toString() {
    return getName();
  }
}
