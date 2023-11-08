package rasterOp.PolygonRasterizers;

import model.Line;
import model.Point;
import model.Polygon;
import rasterData.RasterBI;
import rasterOp.LineRasterizers.LineRasterizerBresenham;
import rasterOp.LineRasterizers.Liner;

public class RecrangleRasterizer {

    Liner lineRasterizer = new LineRasterizerBresenham();

    public void drawRectangle(RasterBI img, Polygon polygon){
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
}