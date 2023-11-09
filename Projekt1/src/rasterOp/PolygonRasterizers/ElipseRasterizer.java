package rasterOp.PolygonRasterizers;

import model.Polygon;
import rasterData.RasterBI;

import java.util.ArrayList;

public class ElipseRasterizer implements Polygoner{
    @Override
    public void draw(RasterBI img, Polygon polygon) {
        int circleStartX = polygon.vertexArray.get(0).x;
        int circleStartY = polygon.vertexArray.get(0).y;
        int circleRX = polygon.vertexArray.get(0).x;
        int circleRY = 45;
        int step = 15;

        for(int theta = 0; theta < 360; theta = theta + step){
            double x = circleStartX + circleRX * Math.cos(theta);
            double y = circleStartY - circleRY * Math.sin(theta);
            img.setColor((int)x, (int)y, 0xf0f0f0);
        }
    }

    @Override
    public void redrawAll(RasterBI img, ArrayList<Polygon> Polygones) {

    }

    public void drawTest(RasterBI img, int startX, int starY){
        int circleStartX = startX;
        int circleStartY = starY;
        int circleRX = 50;
        int circleRY = 25;
        int step = 1;

        for(int theta = 0; theta < 360; theta = theta + step){
            double x = circleStartX + circleRX * Math.cos(theta);
            double y = circleStartY - circleRY * Math.sin(theta);
            img.setColor((int)x, (int)y, 0xf0f0f0);
        }
    }
}
