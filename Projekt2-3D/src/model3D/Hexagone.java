package model3D;

import transforms.Point3D;

public class Hexagone extends Solid {
    public Hexagone(){
        vb.add(new Point3D(-0.3, 0.5, 0)); //1
        vb.add(new Point3D(0.3, 0.5, 0)); //2
        vb.add(new Point3D(0.5, 0.3, 0)); //3
        vb.add(new Point3D(0.5, -0.3, 0)); //4
        vb.add(new Point3D(0.3, -0.5, 0)); //5
        vb.add(new Point3D(-0.3, -0.5, 0)); //6
        vb.add(new Point3D(-0.5, -0.3, 0)); //7
        vb.add(new Point3D(-0.5, 0.3, 0)); //8


        vb.add(new Point3D(-0.3, 0.5,1)); //9
        vb.add(new Point3D(0.3, 0.5,1)); //10
        vb.add(new Point3D(0.5, 0.3,1)); //11
        vb.add(new Point3D(0.5, -0.3,1)); //12
        vb.add(new Point3D(0.3, -0.5,1)); //13
        vb.add(new Point3D(-0.3, -0.5,1)); //14
        vb.add(new Point3D(-0.5, -0.3,1)); //15
        vb.add(new Point3D(-0.5, 0.3,1)); //16


        addIndices(0, 1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 0);
        addIndices(8,9,9,10,10,11,11,12,12,13,13,14,14,15,15,8);
        addIndices(0,1,1,9,9,8,8,0);
        addIndices(1,2,2,10,10,9,9,1);
        addIndices(2,3,3,11,11,10,10,2);
        addIndices(3,4,4,12,12,11,11,3);
        addIndices(4,5,5,13,13,12,12,4);
        addIndices(5,6,6,14,14,13,13,5);
        addIndices(6,7,7,15,15,14,14,6);
        addIndices(7,0,0,8,8,15,15,7);

    }
}
