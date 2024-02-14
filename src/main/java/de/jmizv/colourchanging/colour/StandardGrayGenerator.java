package de.jmizv.colourchanging.colour;

/**
 *
 * @author jmizv
 */
public class StandardGrayGenerator implements GrayGenerator {

  @Override
  public int toGray(int colour) {
    //      Y = 0.2126 R + 0.7152 G + 0.0722 B
    double r = ((colour >> 16) & 0xff) * 0.2126;
    double g = ((colour >> 8) & 0xff) * 0.7152;
    double b = ((colour) & 0xff) * 0.0722;
    int k = (int) (r + g + b);
    return 0xff000000 | (k << 16) | (k << 8) | k;
  }
}
