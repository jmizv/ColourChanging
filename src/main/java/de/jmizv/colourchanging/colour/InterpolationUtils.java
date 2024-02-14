package de.jmizv.colourchanging.colour;

/**
 * This class contains some useful method for linear interpolation. Bilinear and
 * trilinear methods are also provided.
 *
 * @author jmizv
 */
public class InterpolationUtils {

  /**
   *
   * @param d
   * @param x1
   * @param x2
   * @param f1
   * @param f2
   * @return
   */
  public static double extrapolateLinear(final double d, final double x1, final double x2, final double f1, final double f2) {
    return f1 + (d - x1) / (x2 - x1) * (f2 - f1);
  }

  /**
   *
   * @param x
   * @param f0
   * @param f1
   * @param x0
   * @param x1
   * @return
   */
  public static double convert(final double x, final double f0, final double f1, final double x0, final double x1) {
    return f0 + (f1 - f0) / (x1 - x0) * (x - x0);
  }

////////////////////////////////////////////////////////////////////////////////
  /**
   *
   * @param x
   * @param xMin
   * @param xMax
   * @param y
   * @param yMin
   * @param yMax
   * @param z
   * @param zMin
   * @param zMax
   * @param f000
   * @param f100
   * @param f010
   * @param f001
   * @param f110
   * @param f101
   * @param f011
   * @param f111
   * @return
   */
  public static double[] interpolateTrilinear(
      double[] x, double[] xMin, double[] xMax,
      double[] y, double[] yMin, double[] yMax,
      double[] z, double[] zMin, double[] zMax,
      double[] f000, double[] f100, double[] f010, double[] f001,
      double[] f110, double[] f101, double[] f011, double[] f111) {

    if (x == null) {
      throw new IllegalArgumentException("The first array is null.");
    }
    int length = x.length;
    for (double[] array : new double[][]{xMin, xMax, y, yMin, yMax, z, zMin, zMax, f000, f100, f010, f001, f110, f101, f011, f111}) {
      if (array == null) {
        throw new IllegalArgumentException("Some arrays are null.");
      }
      if (length != array.length) {
        throw new IllegalArgumentException("The arrays have not all the same length, expected " + length + " but found " + array.length);
      }
    }
//      double[] result = new double[length];

    double[] b0 = interpolateBilinear(x, xMin, xMax, y, yMin, yMax, f000, f100, f010, f110);
    double[] b1 = interpolateBilinear(x, xMin, xMax, y, yMin, yMax, f001, f101, f011, f111);

    return interpolateLinear(z, zMin, zMax, b0, b1);
  }

  /**
   * Trilinear interpolation in a unit cube at (0,0,0)
   *
   * @param x
   * @param y
   * @param z
   * @param f000 value at (0,0,0)
   * @param f100 value at (1,0,0)
   * @param f010 value at (0,1,0)
   * @param f001 value at (0,0,1)
   * @param f110 value at (1,1,0)
   * @param f101 value at (1,0,1)
   * @param f011 value at (0,1,1)
   * @param f111 value at (1,1,1)
   * @return
   */
  public static double interpolateTrilinear(
      double x,
      double y,
      double z,
      double f000,
      double f100,
      double f010,
      double f001,
      double f110,
      double f101,
      double f011,
      double f111) {
    return interpolateTrilinear(x, 0, 1, y, 0, 1, z, 0, 1, f000, f100, f010, f001, f110, f101, f011, f111);
  }

  /**
   * Trilinear interpolation.
   *
   * @param x
   * @param xMin lower bound for x
   * @param xMax upper bound for x
   * @param y
   * @param yMin lower bound for y
   * @param yMax upper bound for y
   * @param z
   * @param zMin lower bound for z
   * @param zMax upper bound for z
   * @param f000 value at (0,0,0)
   * @param f100 value at (1,0,0)
   * @param f010 value at (0,1,0)
   * @param f001 value at (0,0,1)
   * @param f110 value at (1,1,0)
   * @param f101 value at (1,0,1)
   * @param f011 value at (0,1,1)
   * @param f111 value at (1,1,1)
   * @return
   */
  public static double interpolateTrilinear(
      double x, double xMin, double xMax,
      double y, double yMin, double yMax,
      double z, double zMin, double zMax,
      double f000, double f100, double f010, double f001,
      double f110, double f101, double f011, double f111) {

    double b0 = interpolateBilinear(x, y, f000, f100, f010, f110);
    double b1 = interpolateBilinear(x, y, f001, f101, f011, f111);

    return interpolateLinear(z, b0, b1);
  }

  /**
   *
   * @param x
   * @param y
   * @param xMin
   * @param xMax
   * @param yMax
   * @param yMin
   * @param values00
   * @param values10
   * @param values01
   * @param values11
   * @return
   * @throws IllegalArgumentException if any input value is null or if not all
   * arrays are of same length
   */
  public static double[] interpolateBilinear(
      double[] x,
      double[] xMin,
      double[] xMax,
      double[] y,
      double[] yMin,
      double[] yMax,
      double[] values00,
      double[] values10,
      double[] values01,
      double[] values11) {
    if (x == null) {
      throw new IllegalArgumentException("The first array is null.");
    }
    int length = x.length;
    for (double[] array : new double[][]{xMin, xMax, y, yMin, yMax, values00, values01, values10, values11}) {
      if (array == null) {
        throw new IllegalArgumentException("Some arrays are null.");
      }
      if (length != array.length) {
        throw new IllegalArgumentException("The arrays have not all the same length, expected " + length + " but found " + array.length);
      }
    }
    double[] result = new double[length];
    for (int i = 0; i < length; i++) {
      result[i] = interpolateBilinear(x[i], xMin[i], xMax[i], y[i], yMin[i], yMax[i], values00[i], values10[i], values01[i], values11[i]);
    }
    return result;
  }

  /**
   *
   * @param x
   * @param xMin
   * @param xMax
   * @param y
   * @param yMin
   * @param yMax
   * @param f00
   * @param f10
   * @param f01
   * @param f11
   * @return
   */
  public static double interpolateBilinear(double x, double xMin, double xMax, double y, double yMin, double yMax, double f00, double f10, double f01, double f11) {
    double k0 = interpolateLinear(x, xMin, xMax, f00, f10);
    double k1 = interpolateLinear(x, xMin, xMax, f01, f11);
    return interpolateLinear(y, yMin, yMax, k0, k1);
  }

  /**
   *
   * @param x
   * @param y
   * @param f00
   * @param f10
   * @param f01
   * @param f11
   * @return
   */
  public static double interpolateBilinear(double x, double y, double f00, double f10, double f01, double f11) {
    return interpolateLinear(
        x,
        interpolateLinear(y, f00, f01),
        interpolateLinear(y, f10, f11));
  }

  /**
   *
   * @param x
   * @param xMin
   * @param xMax
   * @param fMin
   * @param fMax
   * @return
   */
  public static double interpolateLinear(double x, double xMin, double xMax, double fMin, double fMax) {
    return ((xMax - x) * fMin + (x - xMin) * fMax) / (xMax - xMin);
  }

  /**
   *
   * @param x
   * @param fMin
   * @param fMax
   * @return
   */
  public static double interpolateLinear(double x, double fMin, double fMax) {
    return (1 - x) * fMin + x * fMax;
  }

  /**
   *
   * @param f0
   * @param f1
   * @param x0
   * @param x1
   * @param x
   * @return
   */
  public static double linInt(double f0, double f1, double x0, double x1, double x) {
    return f0 * (x1 - x) / (x1 - x0) + f1 * (x - x0) / (x1 - x0);
  }

  /**
   *
   * @param x
   * @param xMin
   * @param xMax
   * @param fMin
   * @param fMax
   * @return
   */
  public static double[] interpolateLinear(double[] x, double[] xMin, double[] xMax, double[] fMin, double[] fMax) {
    double[] result = new double[x.length];
    for (int i = 0; i < result.length; i++) {
      result[i] = interpolateLinear(x[i], xMin[i], xMax[i], fMin[i], fMax[i]);
    }
    return result;
  }

  /**
   *
   * @param x
   * @param fMin
   * @param fMax
   * @return
   */
  public static double[] interpolateLinear(double[] x, double[] fMin, double[] fMax) {
    double[] result = new double[x.length];
    for (int i = 0; i < result.length; i++) {
      result[i] = interpolateLinear(x[i], fMin[i], fMax[i]);
    }
    return result;
  }
}
