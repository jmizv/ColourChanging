package de.jmizv.colourchanging.scale;

import de.jmizv.colourchanging.colour.InterpolationUtils;
import de.jmizv.colourchanging.colour.SimpleColor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author jmizv
 */
public class MultipleGradientScale implements Scale {

  private final List<Pair<Double, SimpleColor>> colors;
  private final double[] segmentBorders;

  /**
   *
   * @param colors
   */
  public MultipleGradientScale(List<Pair<Double, SimpleColor>> colors) {
    Collections.sort(colors, new Comparator<Pair<Double, SimpleColor>>() {
      @Override
      public int compare(Pair<Double, SimpleColor> o1, Pair<Double, SimpleColor> o2) {
        return Double.compare(o1.getLeft(), o2.getLeft());
      }
    });
    this.colors = colors;
    segmentBorders = new double[colors.size()];
    int i = 0;
    for (Pair<Double, SimpleColor> pair : colors) {
      segmentBorders[i++] = pair.getLeft();
    }
  }

  /**
   * Create a new MultipleGradientScale with three colors. Values d1, d2 and d3
   * must be in strict ascending order.
   *
   * @param d1 lower bound
   * @param sc1 lower bound color
   * @param d2 middle bound
   * @param sc2 middle bound color
   * @param d3 upper bound
   * @param sc3 upper bound color
   */
  public MultipleGradientScale(double d1, SimpleColor sc1, double d2, SimpleColor sc2, double d3, SimpleColor sc3) {
    if (d1 >= d2 || d2 >= d3) {
      throw new IllegalArgumentException("Values are not ordered: " + d1 + " >=" + d2 + " or " + d2 + " >= " + d3 + "\nd1>=d2 || d2>=d3");
    }
    this.colors = new ArrayList<Pair<Double, SimpleColor>>(3);
    this.segmentBorders = new double[]{d1, d2, d3};

    colors.add(Pair.of(d1, sc1));
    colors.add(Pair.of(d2, sc2));
    colors.add(Pair.of(d3, sc3));
  }

  /**
   *
   * @param value
   * @return
   */
  @Override
  public double[] getColor(double value) {
    Pair<Double, SimpleColor> p1;
    Pair<Double, SimpleColor> p2;

    if (value <= (p1 = colors.get(0)).getLeft()) {
      return new double[]{p1.getRight().get(0), p1.getRight().get(1), p1.getRight().get(2)};
    }
    if (value >= (p1 = colors.get(colors.size() - 1)).getLeft()) {

      return new double[]{p1.getRight().get(0), p1.getRight().get(1), p1.getRight().get(2)};
    }

    int idx = 0;
    for (; idx < colors.size() - 1; idx++) {
      if (colors.get(idx).getLeft() <= value && colors.get(idx + 1).getLeft() > value) {
        break;
      }
    }
    p1 = colors.get(idx);
    p2 = colors.get(idx + 1);

    double r = InterpolationUtils.convert(value, p1.getRight().get(0), p2.getRight().get(0), p1.getLeft(), p2.getLeft());
    double g = InterpolationUtils.convert(value, p1.getRight().get(1), p2.getRight().get(1), p1.getLeft(), p2.getLeft());
    double b = InterpolationUtils.convert(value, p1.getRight().get(2), p2.getRight().get(2), p1.getLeft(), p2.getLeft());

    return new double[]{r, g, b};
  }

  /**
   *
   * @return
   */
  @Override
  public double getMax() {
    return colors.get(colors.size() - 1).getLeft();
  }

  /**
   *
   * @return
   */
  @Override
  public double getMin() {
    return colors.get(0).getLeft();
  }

  /**
   *
   * @return
   */
  @Override
  public double[] getSegmentBorders() {
    double[] d = new double[segmentBorders.length];
    System.arraycopy(segmentBorders, 0, d, 0, segmentBorders.length);
    return d;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final MultipleGradientScale other = (MultipleGradientScale) obj;
    if (this.colors != other.colors && (this.colors == null || !this.colors.equals(other.colors))) {
      return false;
    }
    if (!Arrays.equals(this.segmentBorders, other.segmentBorders)) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    int hash = 3;
    hash = 43 * hash + (this.colors != null ? this.colors.hashCode() : 0);
    hash = 43 * hash + Arrays.hashCode(this.segmentBorders);
    return hash;
  }

  /**
   *
   * @return
   */
  public static MultipleGradientScale getRainbowScale() {
    return new MultipleGradientScale(getListRainbow());
  }

  /**
   *
   * @return
   */
  public static MultipleGradientScale getRainbowScale90Degree() {
    return new MultipleGradientScale(getListRainbow90Degree());
  }

  /**
   *
   * @return
   */
  public static MultipleGradientScale getGYORWScale() {
    return new MultipleGradientScale(getListGYORW());
  }

  /**
   * A scale from green-yellow-orange-red-violett.
   *
   * @return
   */
  public static MultipleGradientScale getGYORVScale() {
    return new MultipleGradientScale(getListGYORV());
  }

  /**
   *
   * @return
   */
  public static MultipleGradientScale getGYRScale() {
    return new MultipleGradientScale(getListGYR());
  }

  public static MultipleGradientScale getGYRScale2() {
    return new MultipleGradientScale(getListGYR2());
  }

  /**
   *
   * @return
   */
  private static List<Pair<Double, SimpleColor>> getListGYORW() {
    List<Pair<Double, SimpleColor>> list = new ArrayList<Pair<Double, SimpleColor>>();
    list.add(Pair.of(0.0, SimpleColor.GREEN));
    list.add(Pair.of(0.1 * 90, SimpleColor.YELLOW));
    list.add(Pair.of(0.2 * 90, new SimpleColor(1.0, 0.5, 0.0)));
    list.add(Pair.of(0.5 * 90, SimpleColor.RED));
    list.add(Pair.of(1.0 * 90, SimpleColor.WHITE));
    return list;
  }

  /**
   *
   * @return
   */
  private static List<Pair<Double, SimpleColor>> getListGYORV() {
    List<Pair<Double, SimpleColor>> list = new ArrayList<Pair<Double, SimpleColor>>();
    list.add(Pair.of(0.00, SimpleColor.GREEN));
    list.add(Pair.of(9.00, SimpleColor.YELLOW));
    list.add(Pair.of(18.0, new SimpleColor(1.0, 0.5, 0.0)));
    list.add(Pair.of(45.0, SimpleColor.RED));
    list.add(Pair.of(90.0, new SimpleColor(0.5, 0.0, 0.5)));
    return list;
  }

  /**
   * A scale from green-yellow-orange-red-violet- ... and over more pastell
   * colours back to green.
   *
   * @return
   */
  public static MultipleGradientScale getGYORV180() {
    return new MultipleGradientScale(getListGYORV180());
  }

  public static MultipleGradientScale getGYORV180_Pseudo() {
    return new MultipleGradientScale(getListGYORV_Pseudo_180());
  }

  /**
   *
   * @return
   */
  private static List<Pair<Double, SimpleColor>> getListGYORV180() {
    List<Pair<Double, SimpleColor>> list = new ArrayList<Pair<Double, SimpleColor>>();
    list.add(Pair.of(0.00000000, SimpleColor.GREEN));               //   0 00ff00
    list.add(Pair.of(9.00000000, SimpleColor.YELLOW));              //   5 ffff00
    list.add(Pair.of(18.0000000, new SimpleColor(1.0, 0.5, 0.0)));  //  10 ff8000
    list.add(Pair.of(45.0000000, SimpleColor.RED));                 //  25 ff0000
    list.add(Pair.of(90.0000000, new SimpleColor(0.5, 0.0, 0.5)));  //  50 800080
    list.add(Pair.of(180 - 45.0, new SimpleColor(1.0, 0.5, 0.5)));  //  75 ff8080
    list.add(Pair.of(180 - 18.0, new SimpleColor(1.0, 0.5, 0.5)));  //  90 ff8080
    list.add(Pair.of(180 - 9.00, new SimpleColor(1.0, 1.0, 0.5)));  //  95 ffff80
    list.add(Pair.of(180.000000, new SimpleColor(0.5, 1.0, 0.5)));  // 100 80ff80
    return list;
  }

  /**
   *
   * @return
   */
  public static List<Pair<Double, SimpleColor>> getListGYORV_Pseudo_180() {
    List<Pair<Double, SimpleColor>> list = new ArrayList<Pair<Double, SimpleColor>>();
    list.add(Pair.of(0.00000000, SimpleColor.GREEN));               //   0 00ff00
    list.add(Pair.of(9.00000000, SimpleColor.YELLOW));              //   5 ffff00
    list.add(Pair.of(18.0000000, new SimpleColor(1.0, 0.5, 0.0)));  //  10 ff8000
    list.add(Pair.of(45.0000000, SimpleColor.RED));                 //  25 ff0000
    list.add(Pair.of(90.0000000, new SimpleColor(0.5, 0.0, 0.5)));  //  50 800080
    list.add(Pair.of(180 - 45.0, SimpleColor.RED));                 //  75 ff8080
    list.add(Pair.of(180 - 18.0, new SimpleColor(1.0, 0.5, 0.0)));  //  90 ff8080
    list.add(Pair.of(180 - 9.00, SimpleColor.YELLOW));  //  95 ffff80
    list.add(Pair.of(180.000000, SimpleColor.GREEN));  // 100 80ff80
    return list;
  }

  /**
   *
   * @return
   */
  private static List<Pair<Double, SimpleColor>> getListRainbow() {
    List<Pair<Double, SimpleColor>> list = new ArrayList<Pair<Double, SimpleColor>>();
    list.add(Pair.of(0.0, SimpleColor.RED));
    list.add(Pair.of(1 / 6.0, SimpleColor.YELLOW));
    list.add(Pair.of(2 / 6.0, SimpleColor.GREEN));
    list.add(Pair.of(3 / 6.0, SimpleColor.CYAN));
    list.add(Pair.of(4 / 6.0, SimpleColor.BLUE));
    list.add(Pair.of(5 / 6.0, SimpleColor.MAGENTA));
    list.add(Pair.of(6 / 6.0, SimpleColor.RED));
    return list;
  }

  /**
   *
   * @return
   */
  private static List<Pair<Double, SimpleColor>> getListRainbow90Degree() {
    List<Pair<Double, SimpleColor>> list = new ArrayList<Pair<Double, SimpleColor>>();
    list.add(Pair.of(0.0, SimpleColor.RED));
    list.add(Pair.of(18.0, SimpleColor.YELLOW));
    list.add(Pair.of(36.0, SimpleColor.GREEN));
    list.add(Pair.of(54.0, SimpleColor.CYAN));
    list.add(Pair.of(72.0, SimpleColor.BLUE));
    list.add(Pair.of(90.0, SimpleColor.MAGENTA));
//    list.add(Pair.of(90.0, SimpleColor.RED));
    return list;
  }

  /**
   *
   * @return
   */
  private static List<Pair<Double, SimpleColor>> getListRainbow180Degree_TRUE() {
    List<Pair<Double, SimpleColor>> list = new ArrayList<Pair<Double, SimpleColor>>();
    list.add(Pair.of(0.0, SimpleColor.RED));
    list.add(Pair.of(18.0 * 2, SimpleColor.YELLOW));
    list.add(Pair.of(36.0 * 2, SimpleColor.GREEN));
    list.add(Pair.of(54.0 * 2, SimpleColor.CYAN));
    list.add(Pair.of(72.0 * 2, SimpleColor.BLUE));
    list.add(Pair.of(90.0 * 2, SimpleColor.MAGENTA));
//    list.add(Pair.of(90.0, SimpleColor.RED));
    return list;
  }

  public static MultipleGradientScale getRainbow180Degree_TRUE() {
    return new MultipleGradientScale(getListRainbow180Degree_TRUE());
  }

  public static MultipleGradientScale getRainbow180Degree() {
    return new MultipleGradientScale(getListRainbow180Degree());
  }

  private static List<Pair<Double, SimpleColor>> getListRainbow180Degree() {
    List<Pair<Double, SimpleColor>> list = new ArrayList<Pair<Double, SimpleColor>>();

    list.add(Pair.of(0.00, SimpleColor.GREEN));
    list.add(Pair.of(9.00, SimpleColor.YELLOW));
    list.add(Pair.of(18.0, new SimpleColor(1.0, 0.5, 0.0)));
    list.add(Pair.of(45.0, SimpleColor.RED));
    list.add(Pair.of(90.0, new SimpleColor(0.5, 0.0, 0.5)));
    list.add(Pair.of(180., new SimpleColor(0.5, 1, 0.5)));

    return list;
  }

  public static MultipleGradientScale getScaleRainbow_0_1() {
    return new MultipleGradientScale(getListRainbow_0_1());
  }

  private static List<Pair<Double, SimpleColor>> getListRainbow_0_1() {
    List<Pair<Double, SimpleColor>> list = new ArrayList<Pair<Double, SimpleColor>>();

    list.add(Pair.of(0. / 6, new SimpleColor(1.0, 0.0, 0.0)));
    list.add(Pair.of(1. / 6, new SimpleColor(1.0, 1.0, 0.0)));
    list.add(Pair.of(2. / 6, new SimpleColor(0.0, 1.0, 0.0)));
    list.add(Pair.of(3. / 6, new SimpleColor(0.0, 1.0, 1.0)));
    list.add(Pair.of(4. / 6, new SimpleColor(0.0, 0.0, 1.0)));
    list.add(Pair.of(5. / 6, new SimpleColor(1.0, 0.0, 1.0)));
    list.add(Pair.of(6. / 6, new SimpleColor(1.0, 0.0, 0.0)));

    return list;
  }

  /**
   *
   * @return
   */
  private static List<Pair<Double, SimpleColor>> getListGYR() {
    List<Pair<Double, SimpleColor>> list = new ArrayList<Pair<Double, SimpleColor>>();
    list.add(Pair.of(0.0, SimpleColor.RED));
    list.add(Pair.of(0.2, SimpleColor.YELLOW));
    list.add(Pair.of(1.0, SimpleColor.GREEN));
    return list;
  }

  /**
   *
   * @return
   */
  private static List<Pair<Double, SimpleColor>> getListGYR2() {
    List<Pair<Double, SimpleColor>> list = new ArrayList<Pair<Double, SimpleColor>>();
    list.add(Pair.of(0.0, SimpleColor.RED));
    list.add(Pair.of(0.05, SimpleColor.YELLOW));
    list.add(Pair.of(0.2, SimpleColor.GREEN));
    list.add(Pair.of(1.0, SimpleColor.WHITE));
    return list;
  }
}
