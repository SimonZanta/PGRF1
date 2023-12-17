package model3D.Axis;

import model3D.Solid;
import transforms.Point3D;

public class AxisZ extends Solid {
    public AxisZ(){
        vb.add(new Point3D(0, 0, 0));
        vb.add(new Point3D(0, 0, 1));

        addIndices(0,1);
    }
}
