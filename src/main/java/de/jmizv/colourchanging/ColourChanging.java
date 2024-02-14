package de.jmizv.colourchanging;

import de.jmizv.colourchanging.colour.ColorUtils;
import de.jmizv.colourchanging.colour.GrayGenerator;
import de.jmizv.colourchanging.colour.StandardGrayGenerator;
import de.jmizv.colourchanging.scale.Scale;

import java.awt.image.BufferedImage;

public class ColourChanging {

  private final BufferedImage bi;
  private BufferedImage gray;
  private GrayGenerator gg;
  private Scale scale;

  public ColourChanging(BufferedImage bi) {
    if (bi == null) {
      throw new IllegalArgumentException("Image is null.");
    }
    this.bi = bi;
  }

  public Scale getScale() {
    return scale;
  }

  public void setScale(Scale scale) {
    this.scale = scale;
  }

  public void setGrayGenerator(GrayGenerator gg) {
    this.gg = gg;
  }

  private void generateGray() {
    if (gray != null) {
      return;
    }
    if (gg == null) {
      gg = new StandardGrayGenerator();
    }

    gray = new BufferedImage(bi.getWidth(), bi.getHeight(), bi.getType());
    for (int i = 0; i < bi.getWidth(); i++) {
      for (int j = 0; j < bi.getHeight(); j++) {
        gray.setRGB(i, j, gg.toGray(bi.getRGB(i, j)));
      }
    }
  }

  public BufferedImage generate(double step) {
    if (step < 0 || step > 1) {
      throw new IllegalArgumentException("Value is out of range: " + step);
    }
    generateGray();
    BufferedImage instance = new BufferedImage(bi.getWidth(), bi.getHeight(), bi.getType());
    for (int i = 0; i < bi.getWidth(); i++) {
      for (int j = 0; j < bi.getHeight(); j++) {
        instance.setRGB(i, j, getRGB(i, j, step));
      }
    }
    return instance;
  }

  private int getRGB(int i, int j, double step) {
    int g = gray.getRGB(i, j) & 0xff;
    double d = g / 255.0 + step;

    while (d > 1) {
      d -= 1;
    }

    return ColorUtils.toInt(scale.getColor(d));
  }
}
