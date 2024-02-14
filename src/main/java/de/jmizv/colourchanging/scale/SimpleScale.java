package de.jmizv.colourchanging.scale;

import de.jmizv.colourchanging.colour.SimpleColor;

/**
 * Simple gradient between two colors.
 *
 * @author jmizv
 */
public class SimpleScale implements Scale {

  private final double min;
  private final double max;
  private final SimpleColor minColor;
  private final SimpleColor maxColor;
  private final double[] $;
  private final double center;

  /**
   * Creates a scale that lineary interpolates between the two given colors. <p
   * /> The interpolation is constant, i.e. exactly in the middle of the min and
   * max values is the average between the two colors. See the other constructor
   * for an explicit setting of this "center".
   *
   * @param min minimum value of this scale.
   * @param max maximum value of this scale.
   * @param minColor color that is associated with the minimum value
   * @param maxColor color that is associated with the maximum value
   */
  public SimpleScale(final double min, final double max, final SimpleColor minColor, final SimpleColor maxColor) {
    this(min, max, minColor, maxColor, (max + min) / 2.0);
  }

  /**
   *
   * @param min
   * @param max
   * @param minColor
   * @param maxColor
   * @param center this value manipulates the center of the two values min and
   * max
   */
  public SimpleScale(final double min, final double max, final SimpleColor minColor, final SimpleColor maxColor, final double center) {
    this.min = min;
    this.max = max;
    this.minColor = minColor;
    this.maxColor = maxColor;
    if (min >= center || max <= center) {
      throw new IllegalArgumentException("The center is not between min and max");
    }
    this.center = center;
    $ = new double[]{
      (maxColor.get(0) - minColor.get(0) / (max - min)),
      (maxColor.get(1) - minColor.get(1) / (max - min)),
      (maxColor.get(2) - minColor.get(2) / (max - min))
    };
  }

  /**
   *
   * @return
   */
  @Override
  public double getMax() {
    return max;
  }

  public SimpleColor getMaxColor() {
    return maxColor;
  }

  /**
   *
   * @return
   */
  @Override
  public double getMin() {
    return min;
  }

  /**
   *
   * @return
   */
  public SimpleColor getMinColor() {
    return minColor;
  }

  /**
   *
   * @return
   */
  public double getCenter() {
    return center;
  }

  /**
   *
   * @param value
   * @return
   */
  @Override
  public double[] getColor(double value) {
    double r = minColor.get(0) + $[0] * (value - min);
    double g = minColor.get(1) + $[1] * (value - min);
    double b = minColor.get(2) + $[2] * (value - min);
    return new double[]{r, g, b};
  }

  /**
   *
   * @return
   */
  @Override
  public double[] getSegmentBorders() {
    return new double[]{getMin(), getMax()};
  }
}
