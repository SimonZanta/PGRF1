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

            // putting ymax if actual value is smaller than ymax
            if(polygonLineArray.get(i).getFirst().y < ymax){
                ymax = polygonLineArray.get(i).getFirst().y;
            }

            // putting ymin if actual value is bigger than ymin
            if(polygonLineArray.get(i).getSecond().y > ymin){
                ymin = polygonLineArray.get(i).getSecond().y;
            }
        }


        ArrayList<Integer> intersections = new ArrayList<>();

        for(int y = ymax; y < ymin; y++) {
            intersections.clear();
            for (int edge = 0; edge < polygonLineArray.size(); edge++) {

                int x1 = polygonLineArray.get(edge).getFirst().x;
                int y1 = polygonLineArray.get(edge).getFirst().y;
                int x2 = polygonLineArray.get(edge).getSecond().x;
                int y2 = polygonLineArray.get(edge).getSecond().y;

                //calculating intersection using trivial algorithm
                if(y > y1 && y < y2){
                    double k = ((double) (x2 - x1)) / ((y2 - y1));
                    double q = x1 - k * y1;

                    int x = (int) Math.round(k * y + q);
                    intersections.add(x);
                }
            }

            //sorting intersections in ascending order
            CalcUtils.BubbleSort(intersections);

            //drawing even intersections
            for (int i = 0; i < intersections.size(); i = i + 2) {
                if (intersections.size() > i + 1) {
                    liner.drawLine(img, new Line(new Point(intersections.get(i), y), new Point(intersections.get(i + 1), y)));
                }
            }
        }
    }
}
