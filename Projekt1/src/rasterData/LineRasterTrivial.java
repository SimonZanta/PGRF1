package rasterData;

import model.Line;
import model.Point;

public class LineRasterTrivial {

    public void LineRasterTrivial(RasterBI img, Line line){
        int x1 = line.getX1();
        int x2 = line.getX2();
        int y1 = line.getY1();
        int y2 = line.getY2();


        int x;
        int y;

        System.out.println("y1: " + y1 + " | y2: " + y2);
        System.out.println("x1: " + x1 + " | x2: " + x2);

        float k = (y2-y1) / (float) (x2-x1);
        float q = y1 - k * x1;

        System.out.println("k: " + k + " | q: " + q);

        if(k < 1){
            x = x1;
            while(x < x2){
                y = Math.round((k * x) + q);

                System.out.println("x: " + x + " | y: " + y);

                Point pixel = new Point(x, y);
                pixel.Draw(img);

                x++;
            }
        }else{
            y = y1;
            while(y < y2){
                x = Math.round((y - q) / k);

                System.out.println("x: " + x + " | y: " + y);

                Point pixel = new Point(x, y);
                pixel.Draw(img);

                y++;
            }
        }
    }
}
