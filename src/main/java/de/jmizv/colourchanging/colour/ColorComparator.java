package de.jmizv.colourchanging.colour;

import java.util.Comparator;

/**
 *
 * @author jmizv
 */
public abstract class ColorComparator implements Comparator<Integer> {

  public static ColorComparator getLuminanceComparator() {
    return new ColorComparator() {
      @Override
      public int compare(Integer o1, Integer o2) {
        int c1 = o1.intValue();
        int c2 = o2.intValue();

        int[] hsv1 = ColorUtils.toIntArray(c1);
        hsv1 = ColorUtils.RGB_HSV(hsv1);

        int[] hsv2 = ColorUtils.toIntArray(c2);
        hsv2 = ColorUtils.RGB_HSV(hsv2);

        int k = hsv1[2] - hsv2[2];
        if (k != 0) {
          return k;
        }

        return hsv1[1] - hsv2[1];
      }
    };
  }

  public static ColorComparator getRGBComparator(final int idx1, final int idx2, final int idx3, final int idx4) {
    return new ColorComparator() {
      @Override
      public int compare(Integer o1, Integer o2) {
        int c1 = o1.intValue();
        int c2 = o2.intValue();

        int[] rgb1 = ColorUtils.toIntArray(c1);
        int[] rgb2 = ColorUtils.toIntArray(c2);

        int k = 0;
        for (int idx : new int[]{idx1, idx2, idx3, idx4}) {
          k = rgb1[idx] - rgb2[idx];
          if (k != 0) {
            return k;
          }
        }

        return 0;
      }
    };
  }

  public static ColorComparator getGrayComparator() {
    final GrayGenerator gg = new StandardGrayGenerator();
    return new ColorComparator() {
      @Override
      public int compare(Integer o1, Integer o2) {
        int toGray1 = gg.toGray(o1);
        int toGray2 = gg.toGray(o2);
        return (0xff & toGray1) - (0xff & toGray2);
      }
    };
  }
}
