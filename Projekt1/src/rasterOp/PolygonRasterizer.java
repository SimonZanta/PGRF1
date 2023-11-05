package rasterOp;

import model.Line;
import model.Point;
import model.Polygon;
import rasterData.RasterBI;

import java.util.ArrayList;

public class PolygonRasterizer{
    ArrayList<Line> lineArray = new ArrayList<>();
    Liner lineRasterizer = new LineRasterizerBresenham();

    public void drawPolygon(RasterBI img, Polygon polygon) {

        for (int i = 0; i < polygon.vertexArray.size(); i++) {
            int indexA = i;
            int indexB = i + 1;
            if (indexB == polygon.vertexArray.size())
                indexB = 0;

            Point pointA = polygon.vertexArray.get(indexA);
            Point pointB = polygon.vertexArray.get(indexB);

            lineRasterizer.drawLine(img, new Line(pointA, pointB), 1);
        }
    }
}
