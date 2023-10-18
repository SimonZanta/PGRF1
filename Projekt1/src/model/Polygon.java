package model;

import java.util.ArrayList;

public class Polygon {
    public ArrayList<Point> vertexArray;
    public Polygon(ArrayList<Point> vertexArray){
        this.vertexArray = vertexArray;
    }
    public void addPoint(Point point){
        vertexArray.add(point);
    }
}
