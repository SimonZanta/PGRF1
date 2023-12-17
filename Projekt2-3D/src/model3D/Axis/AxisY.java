package model3D.Axis;

import model3D.Solid;
import transforms.Point3D;

public class AxisY extends Solid {
    public AxisY(){
        vb.add(new Point3D(0, 0, 0));
        vb.add(new Point3D(0, 1, 0));

        addIndices(0,1);
    }
}
