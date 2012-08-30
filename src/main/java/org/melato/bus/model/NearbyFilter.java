package org.melato.bus.model;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;

import org.melato.gpx.Earth;
import org.melato.gpx.Point;
import org.melato.gpx.Waypoint;

public class NearbyFilter extends AbstractCollection<Waypoint> {
  private Collection<Waypoint> result;
  private Point target;
  private float targetDistance;
  private float latDelta;
  private float lonDelta;
  
  public NearbyFilter(Point target, float distance, Collection<Waypoint> result) {
    super();
    this.result = result;
    this.target = target;
    this.targetDistance = distance;
    latDelta = Earth.latitudeForDistance(distance); 
    lonDelta = Earth.longitudeForDistance(distance, target.getLat()); 
  }
  private boolean isNearby(Waypoint p) {
    if ( Math.abs(p.getLat() - target.getLat()) > latDelta ) {
      return false;
    }
    if ( Math.abs(p.getLon() - target.getLon()) > lonDelta ) {
      return false;
    }
    return Earth.distance(p, target) <= targetDistance;      
  }
  @Override
  public boolean add(Waypoint p) {
    if ( isNearby(p)) {
      result.add(p);
      return true;        
    }
    return false;
  }

  @Override
  public Iterator<Waypoint> iterator() {
    throw new UnsupportedOperationException();
  }

  @Override
  public int size() {
    throw new UnsupportedOperationException();
  }
  
}
