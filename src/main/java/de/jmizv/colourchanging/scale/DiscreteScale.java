package de.jmizv.colourchanging.scale;


import de.jmizv.colourchanging.colour.ColorComparator;
import de.jmizv.colourchanging.colour.ColorUtils;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author jmizv
 */
public class DiscreteScale implements Scale {

  private List<Integer> values;

  public DiscreteScale(BufferedImage image) {
    ObjectHistogramm<Integer> histo = new ObjectHistogramm<Integer>();
    for (int i = 0; i < image.getWidth(); i++) {
      for (int j = 0; j < image.getHeight(); j++) {
        histo.count(lower(image.getRGB(i, j)));
      }
    }

    List<Integer> keySet = new ArrayList<Integer>(histo.getMap().keySet());

    Collections.sort(keySet, ColorComparator.getGrayComparator());
    Collections.sort(keySet, ColorComparator.getRGBComparator(1,2,3,0));
//    Collections.sort(keySet, ColorComparator.getLuminanceComparator());

    values = keySet;
  }

  private int lower(int rgb) {

    int[] r = ColorUtils.toIntArray(rgb);

    for (int i = 1; i < r.length; i++) {
      r[i] = r[i] / 20 * 20;
    }

    return ColorUtils.toInt(r);
  }

  @Override
  public double[] getColor(double value) {
    int idx = (int) (value * (values.size() - 1));
    return ColorUtils.toDoubleArray(values.get(idx));
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
    return new double[]{0.0, 1.0};
  }

  public BufferedImage get(int height) {
    BufferedImage image = new BufferedImage(values.size(), height, BufferedImage.TYPE_INT_ARGB);

    for (int i = 0; i < values.size(); i++) {
      for (int j = 0; j < height; j++) {
        image.setRGB(i, j, values.get(i));
      }
    }

    return image;
  }
}
