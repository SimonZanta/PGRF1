package rasterOp;

import model.Line;
import model.Point;
import rasterData.RasterBI;
import rasterOp.Liner;

import javax.swing.*;
import java.util.ArrayList;

public class LineRasterizerMidpoint extends Liner {
    int counter = 0;
    public LineRasterizerMidpoint(JPanel panel, ArrayList<Line> lines){
        this.panel = panel;
        this.lines = lines;
    }
    @Override
    public void drawLine(RasterBI img, Line line, int dotSize){

        int x1 = line.getFirst().x;
        int x2 = line.getSecond().x;
        int y1 = line.getFirst().y;
        int y2 = line.getSecond().y;

        int sx = (x1 + x2) / 2;
        int sy = (y1 + y2) / 2;


        Point pixel = new Point(sx, sy);

        pixel.Draw(img);

        if((Math.abs(x1-sx) > dotSize) || (Math.abs(y1-sy) > dotSize)){

            Point point1new = new Point(x1, y1);
            Point point2new = new Point(sx, sy);

            Line lineNew = new Line(point1new, point2new);

            drawLine(img, lineNew, dotSize);
        }

        if((Math.abs(x2-sx) > dotSize) || (Math.abs(y2 - sy) > dotSize)){

            Point point1new = new Point(sx, sy);
            Point point2new = new Point(x2, y2);

            Line lineNew = new Line(point1new, point2new);

            drawLine(img, lineNew, dotSize);
        }
    }

}
