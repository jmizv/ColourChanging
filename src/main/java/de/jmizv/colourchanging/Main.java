package de.jmizv.colourchanging;


import de.jmizv.colourchanging.colour.SimpleColor;
import de.jmizv.colourchanging.image.GifSequenceWriter;
import de.jmizv.colourchanging.scale.MultipleGradientScale;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;

/**
 *
 * @author jmizv
 */
public class Main {

  static String path = "C:\\Users\\jmizv\\Pictures\\Animiert";

  public static void main(String[] args) throws Exception {
    BufferedImage bi = ImageIO.read(new File(path, "f2d.png"));
//    if (true) {
//      return;
//    }
//    BufferedImage bi = ImageIO.read(new File("R-2042326-1260385658.jpeg"));

    GifSequenceWriter writer = new GifSequenceWriter(new FileImageOutputStream(new File(path, "f2decay.gif")), BufferedImage.TYPE_INT_ARGB, 100, true);
    ColourChanging c = new ColourChanging(bi);
//    c.setScale(new SimpleScale(0, 1, SimpleColor.BLACK, SimpleColor.YELLOW));
    c.setScale(new MultipleGradientScale(0, SimpleColor.BLACK, .5, new SimpleColor(241, 213, 50), 1, SimpleColor.BLACK));
//    ImageIO.write(((DiscreteScale) c.getScale()).get(10), "PNG", new File(path, "k.png"));
//    c.setScale(MultipleGradientScale.getScaleRainbow_0_1());

    BufferedImage generate;
    for (double d = 0.0; d < 0.99; d += 0.05) {
      generate = c.generate(d);
      ImageIO.write(generate, "PNG", new File(path, "81167991_" + Math.round(d * 20) + ".png"));
      writer.writeToSequence(generate);
    }
    writer.close();

  }
}
