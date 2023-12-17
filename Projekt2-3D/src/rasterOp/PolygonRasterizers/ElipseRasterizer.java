package rasterOp.PolygonRasterizers;

import model.Point;
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
    public void redrawAll(RasterBI img, ArrayList<Polygon> elipses) {
        for(int elipse = 0; elipse < elipses.size(); elipse++){
            //step at 0.04 is the highest step that makes elipse border continuous
            draw(img, elipses.get(elipse), 0.04);
        }
    }

    public void draw(RasterBI img, Polygon elipse, double step){


        int x1 = elipse.vertexArray.get(0).x;
        int y1 = elipse.vertexArray.get(0).y;
        int x2 = elipse.vertexArray.get(1).x;
        int y2 = elipse.vertexArray.get(1).y;

        int width = (x2 - x1)/2;
        int height = (y2 - y1)/2;

        int xCenter = x2 - width;
        int yCenter = y2 - height;


        int circleStartX = xCenter;
        int circleStartY = yCenter;
        int circleRX = width;
        int circleRY = height;

        for(double theta = 0; theta < 360; theta = theta + step){
            double x = circleStartX + circleRX * Math.cos(theta);
            double y = circleStartY - circleRY * Math.sin(theta);
            img.setColor((int)x, (int)y, 0xf0f0f0);
        }
    }
}
