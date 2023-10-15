package rasterOp;

import model.Line;
import rasterData.RasterBI;

import javax.swing.*;
import java.util.ArrayList;

public interface Liner {
  void drawLine(RasterBI img, Line line);

  default void redrawLines(RasterBI img, ArrayList<Line> lines, JPanel panel){
   img.clear(0x000000);

   for(int d = 0; d < lines.size(); d++){
    drawLine(img, lines.get(d));
   }

   panel.repaint();
  }
}
