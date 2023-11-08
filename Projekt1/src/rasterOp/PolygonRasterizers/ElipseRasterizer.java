package rasterOp.PolygonRasterizers;

import model.Polygon;
import rasterData.RasterBI;

import java.util.ArrayList;

public class ElipseRasterizer implements Polygoner{
    @Override
    public void draw(RasterBI img, Polygon polygon) {


    }

    @Override
    public void redrawAll(RasterBI img, ArrayList<Polygon> Polygones) {

    }

    public static void drawTest(){
        // a = 5
        // b = 5
        //x = sqrt(a^2 * (1 - (y^2) / b^2))

        int a = 5;
        int b = 5;
        int y = b*2;

        for(int i = 0; i < y; i++){
            double x = Math.sqrt(1-i);
            System.out.println(x + " " + i);
        }


    }

    public static void main(String[] args){
        drawTest();
    }
}
