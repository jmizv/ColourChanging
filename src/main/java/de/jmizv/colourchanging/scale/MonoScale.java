package de.jmizv.colourchanging.scale;


import de.jmizv.colourchanging.colour.SimpleColor;

/**
 * Pseudo-Scale that returns always the same color.
 *
 * @author jmizv
 */
public class MonoScale implements Scale {

  private final double[] color;

  public MonoScale(SimpleColor colour) {
    if (colour == null) {
      throw new IllegalArgumentException("SimpleColor is null.");
    }
    this.color = colour.get();
  }

  public MonoScale(double[] colour) {
    if (colour == null || colour.length != 3) {
      throw new IllegalArgumentException("Double array is null or has not three elements.");
    }
    this.color = colour;
  }

  @Override
  public double[] getColor(double value) {
    return color;
  }

  @Override
  public double getMax() {
    return 1.0;
  }

  @Override
  public double getMin() {
    return 0.0;
  }

  @Override
  public double[] getSegmentBorders() {
    return new double[]{getMin(), getMax()};
  }
}
