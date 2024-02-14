package de.jmizv.colourchanging.colour;

/**
 *
 * @author jmizv
 */
public class ColorUtils {

  @Deprecated
  public static int[] RGB_Array(int rgb) {
    return toIntArray(rgb);
  }

  @Deprecated
  public static int RGB_Int(int[] rgb) {
    return toInt(rgb);
  }

  public static int[] RGB_CYMK(int[] rgb) {
    return null;
  }

  public static int[] RGB_HSV(int[] rgb) {
    float[] result;
    if (rgb.length == 3) {
      result = RGBtoHSV(rgb[0] / 255.0f, rgb[1] / 255.0f, rgb[2] / 255.0f);
      return new int[]{(int) (result[0] * 255), (int) (result[1] * 255), (int) (result[2] * 255)};
    }
    result = RGBtoHSV(rgb[1] / 255.0f, rgb[2] / 255.0f, rgb[3] / 255.0f);
    return new int[]{rgb[0], (int) (result[0] * 255), (int) (result[1] * 255), (int) (result[2] * 255)};
  }

  public static int[] CYMK_RGB(int[] cymk) {
    return null;/// ###
  }

  public static int[] CYMK_RGB(int c, int m, int y, int k) {
    return CYMK_RGB(new int[]{c, m, y, k});
  }

  public static int[] HSV_RGB(int[] hsv) {
    return null;//####
  }

  public static int[] HSV_RGB(int h, int s, int v) {
    return HSV_RGB(new int[]{h, s, v});
  }

  public static int[] getCYMK(int rgb) {
    int[] _rgb = getRGB(rgb);
    if (_rgb[0] == 0 && _rgb[1] == 0 && _rgb[2] == 0) {
      return new int[]{0, 0, 0, 1};
    }
    double computedC = 1.0 - (_rgb[0] / 255.0);
    double computedM = 1.0 - (_rgb[1] / 255.0);
    double computedY = 1.0 - (_rgb[2] / 255.0);

    double minCMY = Math.min(Math.min(computedC, computedM), computedY);

    computedC = (computedC - minCMY) / (1 - minCMY);
    computedM = (computedM - minCMY) / (1 - minCMY);
    computedY = (computedY - minCMY) / (1 - minCMY);
    double computedK = minCMY;

    return new int[]{(int) (computedC * 255), (int) (computedM * 255), (int) (computedY * 255), (int) (computedK * 255)};
  }

  public static float[] RGBtoHSV(float r, float g, float b) {
    float min, max, delta;
    float h, s, v;

    min = Math.min(r, Math.min(g, b));
    max = Math.max(r, Math.max(g, b));
    v = max;				// v

    delta = max - min;

    if (max != 0) {
      s = delta / max;		// s
    } else {
      // r = g = b = 0		// s = 0, v is undefined
      s = 0;
      h = -1;
      return new float[]{h, s, v};
    }

    if (r == max) {
      h = (g - b) / delta;		// between yellow & magenta
    } else if (g == max) {
      h = 2 + (b - r) / delta;	// between cyan & yellow
    } else {
      h = 4 + (r - g) / delta;	// between magenta & cyan
    }
    h *= 60;				// degrees
    if (h < 0) {
      h += 360;
    }
    return new float[]{h, s, v};
  }

  public static int[] get(int rgb) {
    int[] _rgb = getRGB(rgb);
    int[] hsv = getHSV(rgb);
    int[] cymk = getCYMK(rgb);
    return new int[]{_rgb[0], _rgb[1], _rgb[2], hsv[0], hsv[1], hsv[2], cymk[0], cymk[1], cymk[2], cymk[3]};
  }

  public static int getRGB(int r, int g, int b) {
    int rgb = (r << 16) | (g << 8) | (b);
    return rgb;
  }

  public static int[] getRGB(int rgb) {
    return new int[]{(rgb & 0x00ff0000) >> 16, (rgb & 0x0000ff00) >> 8, (rgb & 0x000000ff)};
  }

  public static int[] getHSV(int rgb) {
    int[] _rgb = getRGB(rgb);
    return _rgb;
  }

  /**
   * Converts the integer value to a double array with four entries.
   *
   * @param argb
   * @return
   */
  public static double[] toDoubleArray(int argb) {
    int a = (argb & -16777216) >> 24;
    int r = (argb & 16711680) >> 16;
    int g = (argb & 65280) >> 8;
    int b = argb & 255;
    return new double[]{a / 255.0, r / 255.0, g / 255.0, b / 255.0};
  }

  /**
   * Converts the doubles in the array to a argb int which can be used with
   * BufferedImages that are of type TYPE_ARGB. Values must be between 0 and 1
   * and the array can have four or three values leaving the alpha out.
   *
   * @param rgb
   * @return
   */
  public static int toInt(double[] rgb) {
    int r = 0;
    int g = 0;
    int b = 0;
    int a = 255;
    int idx = 0;
    if (rgb.length == 4) {
      idx = 1;
      a = (int) (rgb[0] * 255);
    }
    r = (int) (rgb[idx] * 255);
    g = (int) (rgb[idx + 1] * 255);
    b = (int) (rgb[idx + 2] * 255);
    return (a << 24) | (r << 16) | (g << 8) | b;
  }

  public static int toInt(int[] rgb) {
    if (rgb.length == 4) {
      return (rgb[0] << 24) | (rgb[1] << 16) | (rgb[2] << 8) | rgb[3];
    }
    if (rgb.length == 3) {
      return (rgb[0] << 16) | (rgb[1] << 8) | (rgb[2]);
    }
    throw new IllegalArgumentException();
  }

  public static int[] toIntArray(int argb) {
    return new int[]{
              (0xff000000 & argb) >> 24,
              (0xff0000 & argb) >> 16,
              (0xff00 & argb) >> 8,
              (0xff & argb)
            };
  }
}
