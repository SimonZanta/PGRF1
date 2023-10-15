package model;
import rasterData.RasterBI;
import rasterOp.LineRasterizerMidpoint;
import rasterOp.Liner;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Optional;

public class Point {
    public int x, y;
    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void Draw(RasterBI img){
        img.setColor(this.x, this.y, 0xff00ff);
    }

    public Optional<Point> getClosestEndpointInRadius(RasterBI img, Liner liner){

        for(int i = 0; i < liner.lines.size(); i++){

            double radiusFirst = calculateRadius(liner.lines.get(i).getFirst());
            double radiusSecond = calculateRadius(liner.lines.get(i).getSecond());

            Point firstPoint = liner.lines.get(i).getFirst();
            Point secondPoint = liner.lines.get(i).getSecond();

            if(radiusFirst <= 20 || radiusSecond <= 20){

                liner.lines.remove(i);

                liner.redrawLines(img);

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
}
