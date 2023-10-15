package rasterop;

import objectdata.Line;
import objectdata.Point;
import rasterData.Raster;

public interface liner {

    /**
     *
     * @param img
     * @param x1
     * @param x2
     * @param y1
     * @param y2
     * @param color
     */
    void drawline(Raster img, double x1, double x2, double y1, double y2, int color);

    default void drawline(Raster img, Point p1, Point p2){
        double x1 = p1.getX();
        double y1 = p1.getY();
        double x2 = p2.getX();
        double y2 = p1.getX();

        double k = (y2-y1) / (x2-x1);
        double q = y1 - k * x1;
    }

    default void drawline(Raster img, Line line){

    }
}
