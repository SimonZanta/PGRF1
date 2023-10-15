package objectdata;

import rasterData.RasterBI;

public class Point {

    // final makes constant - non changable variable; imutable
    final double x, y;

    public Point(final double x, final double y){
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
