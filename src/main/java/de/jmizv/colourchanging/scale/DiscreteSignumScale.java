package de.jmizv.colourchanging.scale;


import de.jmizv.colourchanging.color.SimpleColor;

/**
 * Scale for values where only the sign is considered.
 */
public class DiscreteSignumScale implements Scale {

  private final SimpleColor[] simpleColors;

  /**
   *
   * @param neg color that will be used for negative values
   * @param nul color that will be used for values equal to zero
   * @param pos color that will be used for positive values
   */
  public DiscreteSignumScale(SimpleColor neg, SimpleColor nul, SimpleColor pos) {
    this.simpleColors = new SimpleColor[]{neg, nul, pos};
  }

  @Override
  public double[] getColor(double value) {
    if (value < 0) {
      return new double[]{simpleColors[0].get(0), simpleColors[0].get(1), simpleColors[0].get(2)};
    }
    if (value > 0) {
      return new double[]{simpleColors[2].get(0), simpleColors[2].get(1), simpleColors[2].get(2)};
    }
    return new double[]{simpleColors[1].get(0), simpleColors[1].get(1), simpleColors[1].get(2)};
  }

  @Override
  public double getMax() {
    return 1.0;
  }

  @Override
  public double getMin() {
    return -1.0;
  }

  @Override
  public double[] getSegmentBorders() {
    return new double[]{-1.0, 0.0, 1.0};
  }
}
