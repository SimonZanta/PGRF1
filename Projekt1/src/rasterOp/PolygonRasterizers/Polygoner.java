package rasterOp.PolygonRasterizers;

import model.Polygon;
import rasterData.RasterBI;

import java.util.ArrayList;

public interface Polygoner {
    public void draw(RasterBI img, Polygon polygon);

    public void redrawAll(RasterBI img, ArrayList<Polygon> Polygones);
}
