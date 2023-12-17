package model3D.Axis;

import model3D.Solid;
import transforms.Point3D;

public class AxisX extends Solid {
    public AxisX() {
        vb.add(new Point3D(0, 0, 0));
        vb.add(new Point3D(1, 0, 0));

        addIndices(0,1);
    }
}
