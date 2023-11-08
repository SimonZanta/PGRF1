package rasterOp.PolygonRasterizers;

import model.Line;
import model.Point;
import model.Polygon;
import rasterData.RasterBI;
import rasterOp.LineRasterizers.LineRasterizerBresenham;
import rasterOp.LineRasterizers.Liner;

import java.util.ArrayList;

public class PolygonRasterizer implements Polygoner{

    Liner lineRasterizer = new LineRasterizerBresenham();
    public void draw(RasterBI img, Polygon polygon) {

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

    @Override
    public void redrawAll(RasterBI img, ArrayList<Polygon> Polygones) {

    }
}
