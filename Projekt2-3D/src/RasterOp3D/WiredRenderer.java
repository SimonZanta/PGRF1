package RasterOp3D;

import model.Line;
import rasterData.RasterBI;
import rasterOp.LineRasterizers.Liner;
import model3D.Solid;
import transforms.Mat4;
import transforms.Mat4Identity;
import transforms.Point3D;
import transforms.Vec3D;

import java.util.ArrayList;

public class WiredRenderer {
    private Liner lineRasterizer;
    private Mat4 view;
    private Mat4 proj;

    public WiredRenderer(Liner lineRasterizer) {
        this.lineRasterizer = lineRasterizer;
        this.view = new Mat4Identity();
    }

    public void render(RasterBI img, Solid solid, int color) {
        // Solid má index buffer, projdu ho v cyklu
        // pro každé dva prvky si načtu odpovídající vertex
        // spojím vertexy linou

        for (int i = 0; i < solid.getIb().size(); i += 2) {
            int indexA = solid.getIb().get(i);
            int indexB = solid.getIb().get(i + 1);

            Point3D a = solid.getVb().get(indexA);
            Point3D b = solid.getVb().get(indexB);

            a = a.mul(solid.getModel()).mul(view).mul(proj);
            b = b.mul(solid.getModel()).mul(view).mul(proj);

            // TODO: ořezání

            if (!checkVertex(a)) {
                continue;
            }

            if (!checkVertex(b)) {
                continue;
            }
            
            // TODO: dehomogenizace

            Point3D dehomogA = a.mul(1/ a.getW());
            Point3D dehomogB = b.mul(1/ b.getW());

            Vec3D v1 = transformToWindow(new Vec3D(dehomogA), img);
            Vec3D v2 = transformToWindow(new Vec3D(dehomogB), img);

            lineRasterizer.drawLine(img, color, new Line((int)Math.round(v1.getX()), (int)Math.round(v1.getY()),
                            (int)Math.round(v2.getX()), (int)Math.round(v2.getY())));
        }
    }

    public Vec3D transformToWindow(Vec3D p, RasterBI img) {
        return p.mul(new Vec3D(1, -1, 1))
                .add(new Vec3D(1, 1, 0))
                .mul(new Vec3D((1000 - 1) / 2., (800 - 1) / 2., 1));
    }

    public void renderScene(RasterBI img, ArrayList<Solid> scene, Solid currentModel) {
        for (Solid solid : scene)
            if(solid == currentModel){
                render(img, solid, 0xff0000);
            }else{
                render(img, solid, 0xff00ff);
            }

    }

    public void setView(Mat4 view) {
        this.view = view;
    }

    public void setProj(Mat4 proj) {
        this.proj = proj;
    }

    private boolean checkVertex(Point3D vertex) {
        double x = vertex.getX();
        double y = vertex.getY();
        double z = vertex.getZ();
        double w = vertex.getW();

        return -w <= x && x <= w && -w <= y && y <= w && 0 <= z && z <= w;
    }
}
