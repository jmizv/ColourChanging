package de.jmizv.colourchanging.scale;

import de.jmizv.colourchanging.color.SimpleColor;

/**
 * Simple gradient between two colors.
 */
public class SimpleScale implements Scale {

  private final double min;
  private final double max;
  private final SimpleColor minColor;
  private final SimpleColor maxColor;
  private final double[] steps;

  /**
   * Creates a scale that linearly interpolates between the two given colors. <p
   * /> The interpolation is constant, i.e. exactly in the middle of the min and
   * max values is the average between the two colors. See the other constructor
   * for an explicit setting of this "center".
   *
   * @param min minimum value of this scale.
   * @param max maximum value of this scale.
   * @param minColor color that is associated with the minimum value
   * @param maxColor color that is associated with the maximum value
   */
  public SimpleScale(double min,
                     double max,
                     SimpleColor minColor,
                     SimpleColor maxColor) {
    this.min = min;
    this.max = max;
    this.minColor = minColor;
    this.maxColor = maxColor;
    steps = new double[]{
        (maxColor.get(0) - minColor.get(0) / (max - min)),
        (maxColor.get(1) - minColor.get(1) / (max - min)),
        (maxColor.get(2) - minColor.get(2) / (max - min))
    };
  }

  @Override
  public double getMax() {
    return max;
  }

  public SimpleColor getMaxColor() {
    return maxColor;
  }

  @Override
  public double getMin() {
    return min;
  }

  public SimpleColor getMinColor() {
    return minColor;
  }

  @Override
  public double[] getColor(double value) {
    double r = minColor.get(0) + steps[0] * (value - min);
    double g = minColor.get(1) + steps[1] * (value - min);
    double b = minColor.get(2) + steps[2] * (value - min);
    return new double[]{r, g, b};
  }

  @Override
  public double[] getSegmentBorders() {
    return new double[]{getMin(), getMax()};
  }
}
