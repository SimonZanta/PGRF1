package rasterOp.PolygonRasterizers;

import model.Line;
import model.Point;
import model.Polygon;
import rasterData.RasterBI;
import rasterOp.LineRasterizers.LineRasterizerBresenham;
import rasterOp.LineRasterizers.Liner;

import java.util.ArrayList;

public class RecrangleRasterizer implements Polygoner {

    Liner lineRasterizer = new LineRasterizerBresenham();

    public void draw(RasterBI img, Polygon polygon){
        int x1 = polygon.vertexArray.get(0).x;
        int y1 = polygon.vertexArray.get(0).y;
        int x2 = polygon.vertexArray.get(1).x;
        int y2 = polygon.vertexArray.get(1).y;

        Line line1 = new Line(new Point(x1,y1), new Point(x2, y1));
        Line line2 = new Line(new Point(x1,y1), new Point(x1, y2));
        Line line3 = new Line(new Point(x2,y2), new Point(x2, y1));
        Line line4 = new Line(new Point(x2,y2), new Point(x1, y2));

        lineRasterizer.drawLine(img, line1);
        lineRasterizer.drawLine(img, line2);
        lineRasterizer.drawLine(img, line3);
        lineRasterizer.drawLine(img, line4);

    }

    public static ArrayList<Point> convertToPointArray(Polygon polygon){

        ArrayList<Point> output = new ArrayList<>();
        int x1 = polygon.vertexArray.get(0).x;
        int y1 = polygon.vertexArray.get(0).y;
        int x2 = polygon.vertexArray.get(1).x;
        int y2 = polygon.vertexArray.get(1).y;

        output.add(new Point(x1,y1));
        output.add(new Point(x2, y1));
        output.add(new Point(x2,y2));
        output.add(new Point(x1, y2));

        return output;
    }

    @Override
    public void redrawAll(RasterBI img, ArrayList<Polygon> Polygones) {
        for(int i = 0; i < Polygones.size(); i++){
            draw(img, Polygones.get(i));
        }
    }

}
