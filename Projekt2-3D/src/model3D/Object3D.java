package model3D;

import transforms.Mat4;
import transforms.Mat4Identity;
import transforms.Point3D;

import java.util.ArrayList;
import java.util.Arrays;

public class Object3D {
    ArrayList<Point3D> vertexBuffer = new ArrayList<>();
    ArrayList<Integer> indexBuffer = new ArrayList<>();
    Mat4 modelMatrix = new Mat4Identity();

    public ArrayList<Point3D> getVertexBuffer() {
        return vertexBuffer;
    }

    public ArrayList<Integer> getIndexBuffer() {
        return indexBuffer;
    }

    public Mat4 getModelMatrix() {
        return modelMatrix;
    }

    protected void addIndices(Integer... indices) {
        indexBuffer.addAll(Arrays.asList(indices));
    }
}
