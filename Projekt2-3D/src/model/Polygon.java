package model;

import java.util.ArrayList;

public class Polygon {

    //TODO
    // create instancing of this class
    public ArrayList<Point> vertexArray;
    public Polygon(ArrayList<Point> vertexArray){
        this.vertexArray = vertexArray;
    }
    public void addPoint(Point point){
        vertexArray.add(point);
    }
    public int getSize(){
        return this.vertexArray.size();
    }
    public ArrayList<Point> getVertexArray(){
        return this.vertexArray;
    }
    public ArrayList<Line> getEdges(){
        ArrayList<Line> edges = new ArrayList<>();
        for(int point = 0; point < vertexArray.size(); point++){

            int indexA = point;
            int indexB = point + 1;
            if (indexB == vertexArray.size()){
                indexB = 0;
            }

            Line edge = new Line(vertexArray.get(indexA), vertexArray.get(indexB));

            edges.add(edge);
        }
        return edges;
    }
}
