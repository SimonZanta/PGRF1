package model3D;

import transforms.Mat4;
import transforms.Mat4Identity;
import transforms.Point3D;

import java.util.ArrayList;
import java.util.Arrays;

public class Solid {
    protected ArrayList<Point3D> vb = new ArrayList<>();
    protected ArrayList<Integer> ib = new ArrayList<>();
    protected Mat4 model = new Mat4Identity();

    public ArrayList<Point3D> getVb() {
        return vb;
    }

    public ArrayList<Integer> getIb() {
        return ib;
    }

    protected void addIndices(Integer... indices) {
        ib.addAll(Arrays.asList(indices));
    }

    public Mat4 getModel() {
        return model;
    }

    public void setModel(Mat4 model) {
        this.model = model;
    }
}
