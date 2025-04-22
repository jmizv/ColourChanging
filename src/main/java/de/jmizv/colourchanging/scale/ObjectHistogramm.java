package de.jmizv.colourchanging.scale;

import java.util.HashMap;
import java.util.Map;

public class ObjectHistogramm<T> {

  private final Map<T, Integer> map = new HashMap<>();

  public void count(T t) {
    if (!map.containsKey(t)) {
      map.put(t, 1);
    } else {
      map.put(t, map.get(t) + 1);
    }
  }

  public int getAmount(T t) {
    return map.get(t);
  }

  public Map<T, Integer> getMap() {
    return map;
  }
}
