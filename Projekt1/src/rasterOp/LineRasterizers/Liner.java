package rasterOp.LineRasterizers;
import model.Line;
import rasterData.RasterBI;

import java.util.ArrayList;

public abstract class Liner {
    public ArrayList<Line> lines;
  public abstract void drawLine(RasterBI img, Line line, int dotSize);

  public abstract void drawLine(RasterBI img, Line line);

  public abstract void deleteLine(RasterBI img, Line line);
  public void redrawLines(RasterBI img, ArrayList<Line> lines){

   for(int d = 0; d < lines.size(); d++){
    drawLine(img, lines.get(d), 1);
   }
  }

}
