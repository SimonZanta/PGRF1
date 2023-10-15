package rasterOp;

import model.Line;
import rasterData.RasterBI;

import javax.swing.*;
import java.util.ArrayList;

public abstract class Liner {
    public JPanel panel;
    public ArrayList<Line> lines;
  public abstract void drawLine(RasterBI img, Line line, int dotSize);

  public void redrawLines(RasterBI img){

   for(int d = 0; d < lines.size(); d++){
    drawLine(img, lines.get(d), 1);
   }

   panel.repaint();
  }

}
