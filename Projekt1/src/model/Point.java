package model;
import rasterData.RasterBI;
import rasterOp.LineRasterizerMidpoint;
import rasterOp.Liner;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Optional;

public class Point {
    public int x, y;
    Liner lineRaster = new LineRasterizerMidpoint();
    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void Draw(RasterBI img){
        img.setColor(this.x, this.y, 0xff00ff);
    }


    public Optional<Point> getClosestEndpointInRadius(RasterBI img, ArrayList<Line> lines, JPanel panel){

        for(int i = 0; i < lines.size(); i++){

            double radiusFirst = calculateRadius(lines.get(i).getFirst());
            double radiusSecond = calculateRadius(lines.get(i).getSecond());

            Point firstPoint = lines.get(i).getFirst();
            Point secondPoint = lines.get(i).getSecond();

            if(radiusFirst <= 20 || radiusSecond <= 20){

                lines.remove(i);

                lineRaster.redrawLines(img, lines, panel);

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
