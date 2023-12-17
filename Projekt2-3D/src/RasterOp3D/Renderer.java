package RasterOp3D;

import model.Line;
import model3D.Object3D;
import model3D.Scene;
import rasterData.RasterBI;
import rasterOp.LineRasterizers.Liner;
import transforms.Mat4;
import transforms.Point3D;
import transforms.Vec2D;
import transforms.Vec3D;

import java.util.ArrayList;
import java.util.Arrays;

public class Renderer {

/*    public void render(RasterBI rasterBI, Scene scene, Liner liner, Mat4 viewMat, Mat4 projectionMat){

        for(int modelI = 0; modelI < scene.getObject3DList().size(); modelI++){

            Object3D model3D = scene.getObject3DList().get(modelI);
            Mat4 transform = model3D.getModelMatrix().mul(viewMat).mul(projectionMat);

            renderObject(rasterBI, model3D, liner, transform);
        }

    }

    private void renderObject(RasterBI rasterBI, Object3D model3D, Liner liner, Mat4 transform) {
        ArrayList<Point3D> transformedPoints = new ArrayList<>();

        for(int vertexI = 0; vertexI < model3D.getVertexBuffer().size(); vertexI++){
            Point3D point = model3D.getVertexBuffer().get(vertexI);
            Point3D transformedPoint = point.mul(transform);

            transformedPoints.add(transformedPoint);
        }

        for(int indexI = 0; indexI < model3D.getIndexBuffer().size(); indexI += 2){
            int firstIndex = model3D.getIndexBuffer().get(indexI);
            int secondIndex = model3D.getIndexBuffer().get(indexI + 1);

            drawLine(rasterBI, transformedPoints.get(firstIndex), transformedPoints.get(secondIndex), liner);
        }
    }

    private void drawLine(RasterBI rasterBI, Point3D first3DPoint, Point3D second3DPoint, Liner liner) {
        *//*first3DPoint.dehomog().ifPresent(p1 -> {
            second3DPoint.dehomog().ifPresent(p2 -> {
                Vec2D first2D = p1.ignoreZ();
                Vec2D second2D = p2.ignoreZ();
            });
        });*//*


        Vec3D first = transformToWindow(first3DPoint.ignoreW());
        Vec3D second = transformToWindow(second3DPoint.ignoreW());

        liner.drawLine(
                rasterBI, new Line((int)Math.round(first.getX()), (int)Math.round(first.getY()),
                        (int)Math.round(second.getX()), (int)Math.round(second.getY()))
        );
    }*/

    // Benesh implementation
    public void renderBenesh(RasterBI rasterBI, Object3D object3D, Liner liner, Mat4 viewMat, Mat4 projectionMat) {

        for(int i = 0; i < object3D.getIndexBuffer().size(); i += 2){
            int indexA = object3D.getIndexBuffer().get(i);
            int indexB = object3D.getIndexBuffer().get(i + 1);

            Point3D a = object3D.getVertexBuffer().get(indexA);
            Point3D b = object3D.getVertexBuffer().get(indexB);

            a = a.mul(object3D.getModelMatrix()).mul(viewMat).mul(projectionMat);
            b = b.mul(object3D.getModelMatrix()).mul(viewMat).mul(projectionMat);

            // TODO: Clip
            /*if (!checkVertex(a)) {
                continue;
            }

            if (!checkVertex(b)) {
                continue;
            }*/

            // TODO: Dehomog

            // Transformace do okna obrazovky

            Point3D dehomogA = a.mul(1/ a.getW());
            Point3D dehomogB = b.mul(1/ b.getW());

            Vec3D va = transformToWindow(new Vec3D(dehomogA), rasterBI);
            Vec3D vb = transformToWindow(new Vec3D(dehomogB), rasterBI);

            liner.drawLine(rasterBI, new Line((int)Math.round(va.getX()), (int)Math.round(va.getY()),
                    (int)Math.round(vb.getX()), (int)Math.round(vb.getY()))
            );
        }
    }

    private Vec3D transformToWindow(Vec3D p, RasterBI raster) {
        return p.mul(new Vec3D(1,-1,1))
                .add(new Vec3D(1, 1, 0))
                .mul(new Vec3D((raster.getWidth()-1)/2., (raster.getHeight() - 1) / 2., 1));
    }

    private boolean checkVertex(Point3D vertex) {
        double x = vertex.getX();
        double y = vertex.getY();
        double z = vertex.getZ();
        double w = vertex.getW();

        return -w <= x && x <= w && -w <= y && y <= w && 0 <= z && z <= w;
    }
}
