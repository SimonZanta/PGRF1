package Utils;

import model.Line;
import model.Point;

public class CalcUtils {
    public Line orientateTwoPointsToLine(Point point1, Point point2){
        int x1 = point1.x;
        int y1 = point1.y;
        int x2 = point2.x;
        int y2 = point2.y;

        if(point1.y > point2.y){
            y1 = point2.y;
            y2 = point1.y;

            x1 = point2.x;
            x2 = point1.x;
        }

        return new Line(x1-1, y1-1, x2, y2);
    }
}
