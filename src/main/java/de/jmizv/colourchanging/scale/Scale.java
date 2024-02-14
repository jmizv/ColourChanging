package de.jmizv.colourchanging.scale;

/**
 * Simple class for wrapping the use of a scala.
 *
 * @author jmizv
 */
public interface Scale {

  /**
   * Calls glColorXX to change the current color according to the given value and the properties of this scale.<p />
   * If the vaue is outside of the bounds of this scale this method should not throw an exception. Instead it should
   * draw the color that is at the next bound.
   *
   * @param drawable
   * @param value The current value for this scale
   */
//  public void setColor(GLAutoDrawable drawable, double value);

  /**
   * Returns the color for the value as double array.
   * @param value
   * @return
   */
  public double[] getColor(double value);

  /**
   *
   * @return the maximal value of this scale.
   */
  public double getMax();

  /**
   *
   * @return the minimal value of this scale.
   */
  public double getMin();

  /**
   * Returns the ordered values between two segments of a scale including the
   * minimum and maximum.
   * @return
   */
  public double[] getSegmentBorders();
}
