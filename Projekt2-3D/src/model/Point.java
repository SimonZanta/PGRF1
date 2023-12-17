package model;
import rasterData.RasterBI;
import rasterOp.LineRasterizers.Liner;

import java.util.ArrayList;
import java.util.Optional;

public class Point {
    public int x, y;
    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void Draw(RasterBI img, int color){
        img.setColor(this.x, this.y, color);
    }

    public Optional<Point> getClosestEndpointInRadius(RasterBI img, Liner liner, ArrayList<Line> lines){

        for(int i = 0; i < lines.size(); i++){

            double radiusFirst = calculateRadius(lines.get(i).getStart());
            double radiusSecond = calculateRadius(lines.get(i).getEnd());

            Point firstPoint = lines.get(i).getStart();
            Point secondPoint = lines.get(i).getEnd();

            if(radiusFirst <= 20 || radiusSecond <= 20){

                lines.remove(i);

                liner.redrawLines(img, lines);

                if(radiusFirst <= 20){
                    return Optional.of(secondPoint);
                }else if(radiusSecond <= 20){
                    return Optional.of(firstPoint);
                }
            }
        }
        return Optional.empty();
    }

    public double calculateRadius(Point start){
        return Math.sqrt(Math.pow((start.x - this.x), 2) + Math.pow((start.y - this.y), 2));
    }

    public double length(){
        return Math.sqrt(x * x + y * y);
    }
}
