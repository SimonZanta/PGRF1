package rasterOp.LineRasterizers;

import model.Line;
import model.Point;
import rasterData.RasterBI;

import java.util.ArrayList;

public class LineRasterizerMidpoint extends Liner {

    /*
    * pro rasterizaci úsečky jsem využil Midpoint algoritmu
    * hlavní výhodou je snadnost implentace a automatická implementace pro všechny kvadrantry
    * nevýhodou je použití rekurze a oproti Bressenhamovu algoritmu také složitější implementace antialiasing algoritmu
    */
    public LineRasterizerMidpoint(ArrayList<Line> lines){
        this.lines = lines;
    }
    @Override
    public void drawLine(RasterBI img, Line line, int dotSpace){
        midpoitAlgorithm(img, line, dotSpace, 0xff0000);
    }

    @Override
    public void drawLine(RasterBI img, Line line) {
        midpoitAlgorithm(img, line, 1, 0xff0000);
    }

    @Override
    public void deleteLine(RasterBI img, Line line) {
        midpoitAlgorithm(img, line, 1,0xffffff);
    }

    public void midpoitAlgorithm(RasterBI img, Line line, int dotSpace, int color){

        int x1 = line.getFirst().x;
        int x2 = line.getSecond().x;
        int y1 = line.getFirst().y;
        int y2 = line.getSecond().y;

        int sx = (x1 + x2) / 2;
        int sy = (y1 + y2) / 2;


        Point pixel = new Point(sx, sy);

        pixel.Draw(img, color);

        if((Math.abs(x1-sx) > dotSpace) || (Math.abs(y1-sy) > dotSpace)){

            Point point1new = new Point(x1, y1);
            Point point2new = new Point(sx, sy);

            Line lineNew = new Line(point1new, point2new);

            drawLine(img, lineNew, dotSpace);
        }

        if((Math.abs(x2-sx) > dotSpace) || (Math.abs(y2 - sy) > dotSpace)){

            Point point1new = new Point(sx, sy);
            Point point2new = new Point(x2, y2);

            Line lineNew = new Line(point1new, point2new);

            drawLine(img, lineNew, dotSpace);
        }
    }

}
