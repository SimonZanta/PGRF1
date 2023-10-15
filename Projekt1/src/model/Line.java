package model;
import model.Point;
import rasterData.RasterBI;

public class Line {

    Point first, second;

    public Line(Point point1, Point point2){
        first =  new Point(point1.x, point1.y);
        second =  new Point(point2.x, point2.y);
    }

    public Point getFirst() {
        return first;
    }

    public void setFirst(Point first) {
        this.first = first;
    }

    public Point getSecond() {
        return second;
    }

    public void setSecond(Point second) {
        this.second = second;
    }
}
