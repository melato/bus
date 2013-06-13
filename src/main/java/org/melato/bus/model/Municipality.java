package org.melato.bus.model;

import java.io.Serializable;

import org.melato.gps.Point2D;

public class Municipality implements Serializable {
  public String name;
  public String mayor;
  public String police;
  public String website;
  public String address;
  public String postalCode;
  public String city;
  public Point2D point;

  public String getName() {
    return name;
  }
  
  public String getMayor() {
    return mayor;
  }


  public void setMayor(String mayor) {
    this.mayor = mayor;
  }


  public String getPolice() {
    return police;
  }


  public void setPolice(String police) {
    this.police = police;
  }


  public String getWebsite() {
    return website;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getPostalCode() {
    return postalCode;
  }

  public void setPostalCode(String postalCode) {
    this.postalCode = postalCode;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public Point2D getPoint() {
    return point;
  }

  public void setPoint(Point2D point) {
    this.point = point;
  }

  public void setWebsite(String website) {
    this.website = website;
  }


  public void setName(String name) {
    this.name = name;
  }


  @Override
  public String toString() {
    return name != null ? name : "";
  }

  public Municipality(String name) {
    super();
    this.name = name;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Municipality other = (Municipality) obj;
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    return true;
  }
}
