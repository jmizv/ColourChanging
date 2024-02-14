package de.jmizv.colourchanging.colour;

import java.io.*;

public class RainbowColor
{

  public static final int BB=0;
  public static final int HTML=1;



  public String[][] colorTags = {{"[color=#","]","[/color]"},{"<span style=\"color:#",";\">","</span>"}};

  private String text=new String();
  private File out;
  private File in;
  private String output;

  private int steps=1;
  private boolean cyclic=true;
  private int useCode=1;


  /*Setters*/
  public void setText(String text)
  {this.text=text;}
  public void setFileIn(File in)
  {this.in=in;}
  public void setFileOut(File out)
  {this.out=out;}
  public void setSteps(int steps)
  {this.steps=steps;}
  public void setCyclic(boolean c)
  {cyclic=c;}
  public void setCode(int c)
  {useCode=c;}

  /*Getters*/
  public String getText()
  {return text;}
  public File getFileOut()
  {return out;}
  public File getFileIn()
  {return in;}


  public RainbowColor()
  {}



  private int[][] rgb={{255,0,0},{255,255,0},{0,255,0},{0,255,255},{0,0,255},{255,0,255}};
//  private int[][] rgb={{255,255,255},{0,0,0}};

  public void initRGB()
  {
    if(cyclic)
    {
      int[][] t;
      t=new int[rgb.length+1][3];

      for(int i=0;i<rgb.length;i++)
      {
        t[i][0]=rgb[i][0];
        t[i][1]=rgb[i][1];
        t[i][2]=rgb[i][2];
      }

      t[rgb.length][0]=rgb[0][0];
      t[rgb.length][1]=rgb[0][1];
      t[rgb.length][2]=rgb[0][2];

      rgb=t;
    }
  }


  private String[] hex={"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F"};
  private String intToHex(int i)
  {
    StringBuilder sb = new StringBuilder();
    int rest;
    while(i>=16)
    {
      rest=i%16;
      i/=16;
      sb.append(hex[rest]);
    }
    sb.append(hex[i]);
    return sb.reverse().toString();
  }
  private String intToHexWithLeadingZeros(int i,int zeros)
  {
    String r=intToHex(i);
    StringBuilder sb=new StringBuilder();
    int k=0;
    while(k+r.length()<zeros)
    {
    sb.append("0");
    k++;
    }
    sb.append(r);

    return sb.toString();
  }


  public void doRainbow()
  {
  int length=text.length();
  double intervalLength=((double)length)/((double)(rgb.length-1));

  StringBuilder sb=new StringBuilder();

  double[] rgbDiff=new double[3];
  double[] rgbTemp=new double[3];

  for(int i=0;i<rgb.length-1;i++)
  {
  
  rgbDiff[0]=((double)(rgb[i+1][0]-rgb[i][0]))/intervalLength;
  rgbDiff[1]=((double)(rgb[i+1][1]-rgb[i][1]))/intervalLength;
  rgbDiff[2]=((double)(rgb[i+1][2]-rgb[i][2]))/intervalLength;

  rgbTemp[0]=rgb[i][0];
  rgbTemp[1]=rgb[i][1];
  rgbTemp[2]=rgb[i][2];

    for(int j=(int)(((double)i)*intervalLength);j<(int)(((double)(i+1))*intervalLength);j+=steps)
    {

    sb.append(colorTags[useCode][0]);
    sb.append(intToHexWithLeadingZeros((int)(rgbTemp[0]),2));
    sb.append(intToHexWithLeadingZeros((int)(rgbTemp[1]),2));
    sb.append(intToHexWithLeadingZeros((int)(rgbTemp[2]),2));
    sb.append(colorTags[useCode][1]);
  
    try{
    sb.append(text.substring(j,j+steps));
    }
    catch(Exception e)
    {}

    sb.append(colorTags[useCode][2]);

    rgbTemp[0]+=rgbDiff[0]*steps;//*((double)j)/(((double)i+1.0)*intervalLength);
    rgbTemp[1]+=rgbDiff[1]*steps;//*((double)j)/(((double)i+1.0)*intervalLength);
    rgbTemp[2]+=rgbDiff[2]*steps;//*((double)j)/(((double)i+1.0)*intervalLength);

    }

  }


  output=sb.toString();
  
  }

  public void s(String s)
  {System.out.println(s);}


  public void writeFile()
  {
    PrintWriter pw=null;

    try
    {
    pw=new PrintWriter(new BufferedWriter(new FileWriter("bla.htm")));

    pw.print(output);
    pw.close();

    }
    catch (IOException ioe)
    {
      ioe.printStackTrace();
    }
  };


  private void readFile()
  {
    BufferedReader br=null;
    String line=null;
    StringBuilder sb=new StringBuilder();
    try{
      br=new BufferedReader(new FileReader(in));

      line=br.readLine();
      while(line!=null)
      {
      sb.append(line);
      sb.append("\n");

      line=br.readLine();
      }

    }
    catch(IOException ioe)
    {
      ioe.printStackTrace();
    }

    text=sb.toString();

  }

  public void printUsage()
  {
  System.out.println("How To Use:\n");
  System.out.println("java RainbowColor InputTextFile.file [Mode]");
  System.out.println("Where Mode is {HTML, BE-Code}, standard is bbcdode");
  }



  public static void main(String[] args)
  {
    RainbowColor rc=new RainbowColor();

    for(String a : args)
    {
      if(a.startsWith("-steps:"))
      {
        a=a.substring(7,a.length());
        rc.setSteps(Integer.parseInt(a));
      }
      if(a.startsWith("-s:"))
      {
        a=a.substring(3,a.length());
        rc.setSteps(Integer.parseInt(a));
      }
      if(a.startsWith("-code:HTML") || a.startsWith("-HTML"))
      {
        rc.setCode(RainbowColor.HTML);
      }
      if(a.startsWith("-code:BB") || a.startsWith("-BB"))
      {
        rc.setCode(RainbowColor.BB);
      }
      if(a.startsWith("-cyclic:"))
      {
        a=a.substring(8,a.length());
        if(a.equals("0") || a.startsWith("N") || a.startsWith("n"))
        {
          rc.setCyclic(false);
        }
        if(a.equals("0") || a.startsWith("Y") || a.startsWith("y") || a.startsWith("J") || a.startsWith("j"))
        {
          rc.setCyclic(true);
        }
      }
      if(a.startsWith("-input:"))
      {
        a=a.substring(7,a.length());
        File f=new File(a);
        if(f.isFile())
        {
          rc.setFileIn(f);
        }
      }
    }

    rc.readFile();
    rc.initRGB();
    rc.doRainbow();
    rc.writeFile();

  }
};