package rasterOp;

import model.Line;
import model.Point;
import rasterData.RasterBI;

public class LineRasterizerBresenham extends Liner {
    @Override
    public void drawLine(RasterBI img, Line line, int dotSize) {
        //TODO

        bresenhamAlgorithm(img, line, 0xff00ff);
    }

    @Override
    public void drawLine(RasterBI img, Line line) {
        bresenhamAlgorithm(img, line, 0xff00ff);
    }

    @Override
    public void deleteLine(RasterBI img, Line line) {
        //TODO
    }

    public void bresenhamAlgorithm(RasterBI img, Line line, int color){
        int x1 = line.getFirst().x;
        int x2 = line.getSecond().x;
        int y1 = line.getFirst().y;
        int y2 = line.getSecond().y;

        //TODO
        // create Line using 4 coordinates

        System.out.println(Math.abs(y2 - y1) < Math.abs(x2 - x1));

        if (Math.abs(y2 - y1) < Math.abs(x2 - x1)) {
            if (x1 > x2) {
                plotLineLow(img, new Line(new Point(x2, y2), new Point(x1, y1)), 0x0000ff);
            } else {
                plotLineLow(img, new Line(new Point(x1, y1), new Point(x2, y2)), 0x0000ff);
            }
        }
        else{
            if (y1 > y2) {
                plotLineHigh(img, new Line(new Point(x2, y2), new Point(x1, y1)), 0x0000ff);
            }else{
                plotLineHigh(img, new Line(new Point(x1, y1), new Point(x2, y2)), 0x0000ff);
            }
        }
    }

    public void plotLineLow(RasterBI img, Line line, int color) {
        int x1 = line.getFirst().x;
        int x2 = line.getSecond().x;
        int y1 = line.getFirst().y;
        int y2 = line.getSecond().y;


        int dx = x2 - x1;
        int dy = y2 - y1;
        int yi = 1;

        if (dy < 0){
            yi = -1;
            dy = -dy;
        }

        int D = (2 * dy) - dx;
        int y = y1;

        for(int x = x1; x < x2; x++){
            Point pixel = new Point(x, y);
            pixel.Draw(img, color);

            if (D > 0){
                y = y + yi;
                D = D + (2 * (dy - dx));
            } else{
                D = D + 2 * dy;
            }
        }
    }

    public void plotLineHigh(RasterBI img, Line line, int color) {
        int x1 = line.getFirst().x;
        int x2 = line.getSecond().x;
        int y1 = line.getFirst().y;
        int y2 = line.getSecond().y;

        int dx = x2 - x1;
        int dy = y2 - y1;
        int xi = 1;

        if (dx < 0){
            xi = -1;
            dx = -dx;
        }

        int D = (2 * dx) - dy;
        int x = x1;

        for (int y = y1; y < y2; y++){
            Point pixel = new Point(x, y);
            pixel.Draw(img, color);

            if (D > 0){
                x = x + xi;
                D = D + (2 * (dx - dy));
            }else{
                D = D + 2 * dx;
            }
        }
    }
}
