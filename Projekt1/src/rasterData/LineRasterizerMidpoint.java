package rasterData;

import model.Line;
import model.Point;

public class LineRasterizerMidpoint {
    public void Midpoint(RasterBI img, Line line){

        int x1 = line.getFirst().x;
        int x2 = line.getSecond().x;
        int y1 = line.getFirst().y;
        int y2 = line.getSecond().y;

        int sx = (x1 + x2) / 2;
        int sy = (y1 + y2) / 2;

        Point pixel = new Point(sx, sy);

        pixel.Draw(img);

        if((Math.abs(x1-sx) > 1) || (Math.abs(y1-sy) > 1)){

            Point point1new = new Point(x1, y1);
            Point point2new = new Point(sx, sy);

            Line lineNew = new Line(point1new, point2new);

            Midpoint(img, lineNew);
        }

        if((Math.abs(x2-sx) > 1) || (Math.abs(y2 - sy) > 1)){

            Point point1new = new Point(sx, sy);
            Point point2new = new Point(x2, y2);

            Line lineNew = new Line(point1new, point2new);

            Midpoint(img, lineNew);
        }
    }
}
