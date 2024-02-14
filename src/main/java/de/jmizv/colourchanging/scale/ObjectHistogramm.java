package de.jmizv.colourchanging.scale;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author jmizv
 */
public class ObjectHistogramm<T> {

  private Map<T, Integer> map;

  public ObjectHistogramm() {
    map = new HashMap<T, Integer>();
  }

  public void count(T t) {
    if (!map.containsKey(t)) {
      map.put(t, 1);
    } else {
      map.put(t, map.get(t) + 1);
    }
  }

  public int getAmount(T t) {
    return map.get(t).intValue();
  }

  public Map<T, Integer> getMap() {
    return map;
  }
}
