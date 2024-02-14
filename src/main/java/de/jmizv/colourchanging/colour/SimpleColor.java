package de.jmizv.colourchanging.colour;

import java.text.MessageFormat;
import java.util.Arrays;

/**
 * Provides a RGB double array wrapper class. Values are from 0.0 to 1.0 (dark
 * to light).
 *
 * @author jmizv
 */
public class SimpleColor {

  /**
   * Primary color – Red (1,0,0)
   */
  public final static SimpleColor RED = new SimpleColor(1.0, 0, 0);
  /**
   * Primary color – Green (0,1,0)
   */
  public final static SimpleColor GREEN = new SimpleColor(0, 1.0, 0);
  /**
   * Primary color – Blue (0,0,1)
   */
  public final static SimpleColor BLUE = new SimpleColor(0, 0, 1.0);
  /**
   * Secondary colors – Magenta (1,0,1)
   */
  public final static SimpleColor MAGENTA = new SimpleColor(1.0, 0.0, 1.0);
  /**
   * Secondary colors – Yellow (1,1,0)
   */
  public final static SimpleColor YELLOW = new SimpleColor(1.0, 1.0, 0);
  /**
   * Secondary colors – Cyan (0,1,1)
   */
  public final static SimpleColor CYAN = new SimpleColor(0.0, 1.0, 1.0);
  /**
   * White (not really a colors :D)
   */
  public final static SimpleColor WHITE = new SimpleColor(1.0, 1.0, 1.0);
  /**
   * Black (not really a colors :D)
   */
  public final static SimpleColor BLACK = new SimpleColor(0.0, 0.0, 0.0);
  /**
   * Color for attracting critical points, according to Weinkauf papers.
   */
  public final static SimpleColor W_RED = new SimpleColor(1.0, 0.35, 0.175); // 255,89,45 = #FF592D
  public static final SimpleColor W_RED_LITE = new SimpleColor("#ffbd91"); // 255,89,45 = #FF592D
  public static final SimpleColor W_RED_DARK = new SimpleColor("#9b0000"); // 255,89,45 = #FF592D
  /**
   * Color for repelling critical points, according to Weinkauf papers.
   */
  public final static SimpleColor W_BLUE = new SimpleColor(0.235, 0.45, 1.0); // 60,115,255 = #3C73FF
  public static final SimpleColor W_BLUE_LITE = new SimpleColor("#a0d7ff");
  public static final SimpleColor W_BLUE_DARK = new SimpleColor("#000f9b");
  //
  /**
   *
   */
  private final double[] rgb;

  /**
   *
   * @param r
   * @param g
   * @param b
   */
  public SimpleColor(double r, double g, double b) {
    if (r > 1 || g > 1 || b > 1) {
      throw new IllegalArgumentException(MessageFormat.format("At least one value is greater than 1: ({0},{1},{2})", r, g, b));
    }
    if (r < 0 || g < 0 || b < 0) {
      throw new IllegalArgumentException(MessageFormat.format("At least one value is less than 0: ({0},{1},{2})", r, g, b));
    }
    this.rgb = new double[]{r, g, b};
  }

  /**
   * Dangerous.
   *
   * @param rgb
   */
  public SimpleColor(double[] rgb) {
    this(rgb[0], rgb[1], rgb[2]);
  }

  /**
   *
   * @param r a value for red between 0 and 255, incl.
   * @param g a value for green between 0 and 255, incl.
   * @param b a value for blue between 0 and 255, incl.
   */
  public SimpleColor(int r, int g, int b) {
    this(r / 255.0, g / 255.0, b / 255.0);
  }

  /**
   *
   * @param s HTML-like String representation of a rgb colour, like "#abc123" or
   * "23bc47"
   */
  public SimpleColor(String s) {
    String k = s;
    if (s.startsWith("#")) {
      k = s.substring(1);
    }
    if (k.length() != 6) {
      throw new IllegalArgumentException("invalid string for simple color: " + s + ", expected hexadecimal string");
    }
    double r = Integer.parseInt(k.substring(0, 2), 16) / 255.0;
    double g = Integer.parseInt(k.substring(2, 4), 16) / 255.0;
    double b = Integer.parseInt(k.substring(4, 6), 16) / 255.0;
    this.rgb = new double[]{r, g, b};
  }

  /**
   *
   * @return this red value, between 0 and 1, incl.
   */
  public double getRed() {
    return rgb[0];
  }

  /**
   *
   * @return this green value, between 0 and 1, incl.
   */
  public double getGre() {
    return rgb[1];
  }

  /**
   *
   * @return this blue value, between 0 and 1, incl.
   */
  public double getBlu() {
    return rgb[2];
  }

  /**
   *
   * @param idx 0 for red, 1 for green, 2 for blue, else RuntimeException
   * @return
   */
  public double get(int idx) {
    return rgb[idx];
  }

  /**
   *
   * @return a copy of the rgb values in an array.
   */
  public double[] get() {
    double[] d = new double[3];
    System.arraycopy(rgb, 0, d, 0, 3);
    return d;
  }

  /**
   *
   * @param obj
   * @return
   */
  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final SimpleColor other = (SimpleColor) obj;

    if (!Arrays.equals(this.rgb, other.rgb)) {
      return false;
    }
    return true;
  }

  /**
   *
   * @return
   */
  @Override
  public int hashCode() {
    int hash = 7;
    hash = 37 * hash + Arrays.hashCode(this.rgb);
    return hash;
  }

}
