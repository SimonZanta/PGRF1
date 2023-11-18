package rasterOp;

import model.Edge;
import model.Point;
import model.Polygon;
import rasterData.RasterBI;
import rasterOp.PolygonRasterizers.PolygonRasterizer;

import java.util.ArrayList;

public class PolylineClip {

    public static ArrayList<Point> clip(Polygon subjectPolygon, Polygon clipPolygon){
        ArrayList<Edge> clipPolygonEdge = clipPolygon.getEdges();
        ArrayList<Point> subjectPolygonVert = subjectPolygon.getVertexArray();
        ArrayList<Point> out = subjectPolygonVert;
        ArrayList<Point> prevOut = new ArrayList<>();

        for(int edge = 0; edge < clipPolygonEdge.size(); edge++){
            prevOut.clear();
            prevOut.addAll(out);
            out.clear();

            Edge edgeCurrent = clipPolygonEdge.get(edge);

            for(int i = 0; i < prevOut.size(); i++){

                int indexA = i;
                int indexB = i + 1;
                if (indexB == prevOut.size()){
                    indexB = 0;
                }

                Point p1 = prevOut.get(indexA);
                Point p2 = prevOut.get(indexB);


                if(isInside(p1, edgeCurrent) && isInside(p2, edgeCurrent)){
                    out.add(p2);
                } else if (isInside(p1, edgeCurrent) && !isInside(p2, edgeCurrent)) {
                    Point intersect = getIntersect(p1, p2, edgeCurrent);
                    out.add(intersect);
                } else if (!isInside(p1, edgeCurrent) && isInside(p2, edgeCurrent)) {
                    Point intersect = getIntersect(p1, p2, edgeCurrent);
                    out.add(intersect);
                    out.add(p2);
                }
            }
        }
        return out;
    }

    public static boolean isInside(Point p, Edge edge){
        Point first = edge.getStart();
        Point second = edge.getEnd();

        Point t = new Point(second.x - first.x, second.y - first.y);
        Point n = new Point(-t.y, t.x);
        Point v = new Point(p.x - first.x, p.y - first.y);

        // i get result in format that doesnt go over 1 or -1, i need to save everything to double
        // easiest way to do that was to create 4 coordinates
        double nNormX = n.x / n.length();
        double nNormY = n.y / n.length();
        double vNormX = v.x / v.length();
        double vNormY = v.y / v.length();

        double cosAlpha = nNormX * vNormX + nNormY * vNormY;

        return cosAlpha < 0;
    }
    public static Point getIntersect(Point prev, Point current, Edge clipEdge){
        int x1 = prev.x;
        int y1 = prev.y;
        int x2 = current.x;
        int y2 = current.y;

        int x3 = clipEdge.getStart().x;
        int y3 = clipEdge.getStart().y;
        int x4 = clipEdge.getEnd().x;
        int y4 = clipEdge.getEnd().y;


        int x0 = ((x1*y2 - x2*y1)*(x3-x4) - (x3*y4 - x4*y3)*(x1-x2))/((x1-x2)*(y3-y4)-(y1-y2)*(x3-x4));
        int y0 = ((x1*y2 - x2*y1)*(y3-y4) - (x3*y4 - x4*y3)*(y1-y2))/((x1-x2)*(y3-y4)-(y1-y2)*(x3-x4));

        return new Point(x0, y0);
    }

    public static ArrayList<Point> createData(RasterBI img){
        ArrayList<Point> subjectPolygonVertArr = new ArrayList<>();
        subjectPolygonVertArr.add(new Point(40,30));
        subjectPolygonVertArr.add(new Point(140,30));
        subjectPolygonVertArr.add(new Point(40,120));


        Polygon subjectPolygon = new Polygon(subjectPolygonVertArr);

        ArrayList<Point> clipPolygonVertArr = new ArrayList<>();

        clipPolygonVertArr.add(new Point(80,10));
        clipPolygonVertArr.add(new Point(30,90));
        clipPolygonVertArr.add(new Point(120,110));

        Polygon clipPolygon = new Polygon(clipPolygonVertArr);

        PolygonRasterizer rasterize = new PolygonRasterizer();

        rasterize.draw(img, clipPolygon);
        rasterize.draw(img, subjectPolygon);

        return clip(subjectPolygon, clipPolygon);
    }

    public static void main(String[] args){
        ArrayList<Point> subjectPolygonVertArr = new ArrayList<>();
        subjectPolygonVertArr.add(new Point(40,30));
        subjectPolygonVertArr.add(new Point(140,30));
        subjectPolygonVertArr.add(new Point(40,120));

        Polygon subjectPolygon = new Polygon(subjectPolygonVertArr);

        ArrayList<Point> clipPolygonVertArr = new ArrayList<>();

        clipPolygonVertArr.add(new Point(80,10));
        clipPolygonVertArr.add(new Point(30,90));
        clipPolygonVertArr.add(new Point(120,110));


        Polygon clipPolygon = new Polygon(clipPolygonVertArr);

        ArrayList<Point> clipOut = clip(subjectPolygon, clipPolygon);

        for(int i = 0; i < clipOut.size();i++){
            System.out.println(clipOut.get(i).x + " " + clipOut.get(i).y);
        }
    }
}

