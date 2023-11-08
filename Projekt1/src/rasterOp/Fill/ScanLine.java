package rasterOp.Fill;

import Utils.CalcUtils;
import model.Line;
import model.Point;
import rasterData.RasterBI;
import rasterOp.LineRasterizers.LineRasterizerBresenham;
import rasterOp.LineRasterizers.Liner;

import javax.swing.*;
import java.util.ArrayList;

public class ScanLine {

    ArrayList<Point> polygonArray;
    public ScanLine(ArrayList<Point> polygonArray){
        this.polygonArray = polygonArray;
    }

    //RasterBI img, JPanel panel, Point point, int colorFill
    public void fill(RasterBI img){

        ArrayList<Line> polygonLineArray = new ArrayList<>();
        CalcUtils calcUtils = new CalcUtils();
        Liner liner = new LineRasterizerBresenham();


        // orientating all lines to downwards position
        // removing horizontal lines to it
        for(int i = 0; i < polygonArray.size(); i++){

            int indexA = i;
            int indexB = i + 1;
            if (indexB == polygonArray.size()){
                indexB = 0;
            }

            if(polygonArray.get(indexA).y != polygonArray.get(indexB).y ){
                polygonLineArray.add(calcUtils.orientateTwoPointsToLine(polygonArray.get(indexA), polygonArray.get(indexB)));
            }
        }

        // searching for ymax and ymin
        int ymax = polygonLineArray.get(0).getFirst().y;
        int ymin = polygonLineArray.get(0).getSecond().y;

        for(int i = 0; i < polygonLineArray.size(); i++){
            if(polygonLineArray.get(i).getFirst().y < ymax){
                ymax = polygonLineArray.get(i).getFirst().y;
            }

            if(polygonLineArray.get(i).getSecond().y > ymin){
                ymin = polygonLineArray.get(i).getSecond().y;
            }
        }

        ArrayList<Point> intersections = new ArrayList<>();
        // find intersect using raycast algorithm
        for(int y = ymax; y < ymin; y++) {
            for (int edge = 0; edge < polygonLineArray.size(); edge++) {

                int x1 = polygonLineArray.get(edge).getFirst().x;
                int y1 = polygonLineArray.get(edge).getFirst().y;
                int x2 = polygonLineArray.get(edge).getSecond().x;
                int y2 = polygonLineArray.get(edge).getSecond().y;

                if(y > y1 && y < y2){
                    double k = ((double) (x2 - x1)) / ((y2 - y1));
                    double q = x1 - k * y1;

                    int x = (int) Math.round(k * y + q);
                    intersections.add(new Point(x, y));
                }
            }
        }

        for (int i = 0; i < intersections.size(); i = i + 2) {
                liner.drawLine(img, new Line(intersections.get(i), intersections.get(i+1)));
        }
    }

    public static void main(String[] args){

        int[] numbers = {72, 43, 68, 27, 61, 84, 92, 34, 56, 21, 97, 38, 95, 20, 5, 47};
        /*int[] numbers = {5,1,2,5};*/

        ArrayList<Point> pointArray = new ArrayList<>();

        for(int i = 0; i < numbers.length;  i = i + 2){
            pointArray.add(new Point(numbers[i], numbers[i+1]));
        }

        ScanLine scanLine = new ScanLine(pointArray);

        /*scanLine.fill(img);*/
    }
}
