import java.awt.*;
import java.util.*;

import javax.swing.text.AbstractDocument.LeafElement;

public class SandLab
{
  public static void main(String[] args)
  {
    SandLab lab = new SandLab(120, 80);
    lab.run();
  }
  
  //add constants for particle types here
  public static final int EMPTY = 0;
  public static final int METAL = 1;
  public static final int SAND = 2;
  public static final int WATER = 3;
  public static final int BLACKHOLE = 4;

  
  //do not add any more fields
  private int[][] grid;
  private SandDisplay display;
  
  public SandLab(int numRows, int numCols)
  {
    String[] names;
    names = new String[5];
    names[EMPTY] = "Empty";
    names[METAL] = "Metal";
    names[SAND] = "Sand";
    names[WATER] = "Water";
    //names[BLACKHOLE] = "Black Hole";

    display = new SandDisplay("Falling Sand", numRows, numCols, names);
    grid = new int[numRows][numCols];
  }
  
  //called when the user clicks on a location using the given tool
  private void locationClicked(int row, int col, int tool)
  {
    grid[row][col]=tool;
  }

  //copies each element of grid into the display
  public void updateDisplay()
  {
    for(int r = 0; r<grid.length; r++)
    {
      for(int c = 0; c<grid[r].length; c++)
      {
        switch(grid[r][c])
        {
          case EMPTY: 
            display.setColor(r,c,new Color(0,0,0));
          break;
          case METAL:
            display.setColor(r,c,new Color(54,69,79));
          break;
          case SAND:
            display.setColor(r,c,new Color(255,255,0));
          break;
          case WATER:
            double random = Math.random();
            if(random<0.01)
            {
              display.setColor(r,c,new Color	(15,94,156));
            }
            else if(random<0.02)
            {
              display.setColor(r,c,new Color	(35,137,218));
            }
            else if(random<0.03)
            {
              display.setColor(r,c,new Color	(28,163,236));
            }
            else if(random<0.04)
            {
              display.setColor(r,c,new Color	(90,188,216));
            }
            else if(random<1)
            {
              display.setColor(r,c,new Color	(116,204,244));
            }
        }

      }
    }
    
  }

  //called repeatedly.
  //causes one random particle to maybe do something.
  public void step()
  {
    
      int randY = (int)(Math.random()*grid.length);
      int randX = (int)(Math.random()*grid[0].length);
      //System.out.println(grid[randX][randY]);
      //if(randX!=0 && randX != grid[0].length)
      switch(grid[randY][randX])
      {
        case EMPTY: 
        //System.out.println("HI EMPTY");
        break;
        case METAL:
        //System.out.println("HI METAL");
        break;
        case SAND:
          //System.out.println("HI SAND");
          if(randY!=grid.length-1 && grid[randY+1][randX]==EMPTY)
          {
            grid[randY][randX]=EMPTY;
            grid[randY+1][randX]=SAND;
            //System.out.println("HI1");
          }
          else if(randY!=grid.length-1 && grid[randY+1][randX]==WATER)
          {
            grid[randY][randX]=WATER;
            grid[randY+1][randX]=SAND;
          }
          //grid[randX][randY+1]=SAND;
        break;
        case WATER:
          double random = Math.random();
          if(randY!=grid.length-1 && grid[randY+1][randX]==EMPTY && random<0.33)
          {
            grid[randY][randX]=EMPTY;
            grid[randY+1][randX]=WATER;
          }
          else if(randX != grid[0].length-1 && grid[randY][randX+1]==EMPTY && random<0.66)
          {
            grid[randY][randX+1]=WATER;
            grid[randY][randX]=EMPTY;
          }
          else if(randX != 0 && grid[randY][randX-1]==EMPTY && random<1)
          {
            grid[randY][randX-1]=WATER;
            grid[randY][randX]=EMPTY;
          }
          
      }
      
  }
  
  //do not modify
  public void run()
  {
    while (true)
    {
      for (int i = 0; i < display.getSpeed(); i++)
        step();
      updateDisplay();
      display.repaint();
      display.pause(1);  //wait for redrawing and for mouse
      int[] mouseLoc = display.getMouseLocation();
      if (mouseLoc != null)  //test if mouse clicked
        locationClicked(mouseLoc[0], mouseLoc[1], display.getTool());
    }
  }
}
