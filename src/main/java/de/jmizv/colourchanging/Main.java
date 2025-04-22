package de.jmizv.colourchanging;

import de.jmizv.colourchanging.color.SimpleColor;
import de.jmizv.colourchanging.image.GifSequenceWriter;
import de.jmizv.colourchanging.scale.MultipleGradientScale;
import de.jmizv.colourchanging.scale.SimpleScale;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.tuple.Pair;

public class Main {

  static String path = "C:\\Users\\jmizv\\Pictures\\Animiert";

  public static void main(String[] args) {
    var options = new Options();
    options.addOption(Option.builder("i").longOpt("input").argName("input").hasArg(true).build());
    options.addOption(Option.builder("o").longOpt("output").argName("output").hasArg(true).build());
    options.addOption(Option.builder("s").longOpt("scale").argName("scale").hasArg(true).build());

    // create the parser
    CommandLineParser parser = new DefaultParser();
    try {
      // parse the command line arguments
      CommandLine line = parser.parse(options, args);
      if (!line.hasOption("input")) {
        System.out.println("You must specify a input file.");
        printHelp(options);
        return;
      }
      if (!line.hasOption("output")) {
        System.out.println("You must specify a output file.");
        printHelp(options);
        return;
      }
      String scale;
      if (!line.hasOption("scale")) {
        scale = "SimpleScale";
      } else {
        scale = line.getOptionValue("scale");
      }

      v(line.getOptionValue("input"),
          line.getOptionValue("output"),
          scale);
    } catch (IOException exception) {
      printHelp(options);
      exception.printStackTrace(System.err);
    } catch (ParseException exp) {
      printHelp(options);
      System.err.println("Parsing failed. Reason: " + exp.getMessage());
    }
  }

  private static void printHelp(Options options) {
    HelpFormatter formatter = new HelpFormatter();
    formatter.printHelp("ColorChanging", options);
  }

  private static void v(String input, String output, String scale) throws IOException {
    var file = new File(input);
    if (!file.exists()) {
      System.out.println("Input file " + input + " does not exist.");
      return;
    }
    BufferedImage bi = ImageIO.read(file);

    try (GifSequenceWriter writer = new GifSequenceWriter(new FileImageOutputStream(new File(output)), BufferedImage.TYPE_INT_ARGB, 100, true)) {
      ColourChanging c = new ColourChanging(bi);
      c.setScale(new SimpleScale(0, 1, SimpleColor.BLACK, SimpleColor.YELLOW));
      //c.setScale(new MultipleGradientScale(0, SimpleColor.BLACK, .5, new SimpleColor(241, 213, 50), 1, SimpleColor.BLACK));
      //c.setScale(MultipleGradientScale.getScaleRainbow_0_1());
      c.setScale(new MultipleGradientScale(List.of(Pair.of(0.0, SimpleColor.BLACK),
          Pair.of(0.98, SimpleColor.BLACK),
          Pair.of(0.99, SimpleColor.RED),
          Pair.of(1.0, SimpleColor.RED)

      )
      ));

      for (double d = 0.0; d < 0.999; d += 0.05) {
        BufferedImage generate = c.generate(d);
        //ImageIO.write(generate, "PNG", new File(path, "81167991_" + Math.round(d * 20) + ".png"));
        writer.writeToSequence(generate);
      }
    }
  }
}
