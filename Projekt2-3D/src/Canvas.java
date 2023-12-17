import model3D.Axis.AxisX;
import model3D.Axis.AxisY;
import model3D.Axis.AxisZ;
import model3D.Hexagone;
import model3D.Pyramid;
import rasterData.RasterBI;
import rasterOp.LineRasterizers.LineRasterizerBresenham;
import rasterOp.LineRasterizers.Liner;
import RasterOp3D.WiredRenderer;
import model3D.Cube;
import model3D.Solid;
import transforms.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import Utils.BooleanUtil;

/**
 * trida pro kresleni na platno: vyuzita tridy RasterBufferedImage
 *
 * @author PGRF FIM UHK
 * @version 2023.c04
 */

public class Canvas {
    private JPanel panel;
    private RasterBI raster;
    private Liner lineRasterizer;
    private WiredRenderer wiredRenderer;
    private Solid cube;
    private Solid pyramid;
    private Solid hexagone;
    private Solid axisX;
    private Solid axisY;
    private Solid axisZ;

    private ArrayList<Solid> scene = new ArrayList<>();

    private Camera camera;
    private Mat4 projection;
    private Mat4 projectionOrth;

    private int cameraMoveXStart, cameraMoveYStart;

    private boolean translateObject = false;
    private boolean scaleObject = false;
    private boolean rotateObject = false;

    private boolean orth = false;

    BooleanUtil booleanUtil = new BooleanUtil();

    private int currentObjectIndex;
    private Solid currentObject;

    public Canvas(int width, int height) {
        JFrame frame = new JFrame();
        frame.setLayout(new BorderLayout());
        frame.setTitle("UHK FIM PGRF : " + this.getClass().getName());
        frame.setResizable(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        raster = new RasterBI(width, height);
        lineRasterizer = new LineRasterizerBresenham();
        wiredRenderer = new WiredRenderer(lineRasterizer);

        panel = new JPanel() {
            private static final long serialVersionUID = 1L;

            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                raster.present(g);
            }
        };
        panel.setPreferredSize(new Dimension(width, height));

        frame.add(panel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);

        initScene();

        panel.requestFocus();
        panel.requestFocusInWindow();

        panel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if(e.getKeyCode() == KeyEvent.VK_A)
                    camera = camera.left(0.1);
                if(e.getKeyCode() == KeyEvent.VK_D)
                    camera = camera.right(0.1);
                if(e.getKeyCode() == KeyEvent.VK_W)
                    camera = camera.forward(0.1);
                if(e.getKeyCode() == KeyEvent.VK_S)
                    camera = camera.backward(0.1);

                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    if(currentObjectIndex < scene.size()-1){
                        currentObjectIndex++;
                    } else if (currentObjectIndex == scene.size()-1) {
                        currentObjectIndex = 0;
                    }

                    currentObject = scene.get(currentObjectIndex);

                }

                if(e.getKeyCode() == KeyEvent.VK_T){
                        translateObject = booleanUtil.switchValue(translateObject);
                        scaleObject = false;
                }

                if(translateObject){
                    Mat4Transl transl= new Mat4Transl(0,0,0);
                    switch (e.getKeyCode()){
                        case KeyEvent.VK_RIGHT:
                            transl = new Mat4Transl(1,0,0);
                            break;
                        case KeyEvent.VK_LEFT:
                            transl = new Mat4Transl(-1,0,0);
                            break;
                        case KeyEvent.VK_UP:
                            transl = new Mat4Transl(0,0,1);
                            break;
                        case KeyEvent.VK_DOWN:
                            transl = new Mat4Transl(0,0,-1);
                            break;
                    }
                    currentObject.setModel(currentObject.getModel().mul(transl));
                }

                if(e.getKeyCode() == KeyEvent.VK_F){
                    scaleObject = booleanUtil.switchValue(scaleObject);
                    translateObject = false;
                }

                if(scaleObject){
                    Mat4Scale scaleMat = new Mat4Scale(1,1,1);
                    switch (e.getKeyCode()){
                        case KeyEvent.VK_UP:
                            scaleMat = new Mat4Scale(1.5,1.5,1.5);
                            break;
                        case KeyEvent.VK_DOWN:
                            scaleMat = new Mat4Scale(0.5,0.5,0.5);
                            break;
                    }

                    currentObject.setModel(currentObject.getModel().mul(scaleMat));
                }

                if(e.getKeyCode() == KeyEvent.VK_P){
                    orth = booleanUtil.switchValue(orth);
                    camera = new Camera(
                            new Vec3D(1,-5,2),
                            Math.toRadians(90),
                            Math.toRadians(-15),
                            1. ,
                            true
                    );
                }

                if(e.getKeyCode() == KeyEvent.VK_R){
                    rotateObject = booleanUtil.switchValue(rotateObject);
                }

                if(rotateObject){
                    Mat4RotXYZ matRot = new Mat4RotXYZ( 0, 0, 0);

                    switch (e.getKeyCode()){
                        case KeyEvent.VK_X:
                            matRot = new Mat4RotXYZ(0.17,0,0);
                            break;
                        case KeyEvent.VK_Y:
                            matRot = new Mat4RotXYZ(0,0.17,0);
                            break;
                        case KeyEvent.VK_Z:
                            matRot = new Mat4RotXYZ(0,0,0.17);
                            break;
                    }

                    currentObject.setModel(currentObject.getModel().mul(matRot));
                }

                renderScene();
            }
        });

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                cameraMoveXStart = e.getX();
                cameraMoveYStart = e.getY();
            }
        });

        panel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int cameraMoveXEnd = e.getX();
                int cameraMoveYEnd = e.getY();

                if(cameraMoveXEnd < cameraMoveXStart){
                    camera = camera.addAzimuth(.01);
                    cameraMoveXStart = e.getX();
                }else if(cameraMoveXEnd > cameraMoveXStart){
                    camera = camera.addAzimuth(-.01);
                    cameraMoveXStart = e.getX();
                }


                if(cameraMoveYEnd < cameraMoveYStart){
                    camera = camera.addZenith(.005);
                    cameraMoveYStart = e.getY();
                }else if(cameraMoveYEnd > cameraMoveYStart){
                    camera = camera.addZenith(-.005);
                    cameraMoveYStart = e.getY();
                }

                renderScene();
            }
        });
    }

    public void initScene() {
        camera = new Camera(
                new Vec3D(1,-5,2),
                Math.toRadians(90),
                Math.toRadians(-15),
                1. ,
                true
        );
        projection = new Mat4PerspRH(
                Math.PI / 4,
                raster.getHeight() / (float)raster.getWidth(),
                0.1,
                20.
        );
        projectionOrth = new Mat4OrthoRH(
                raster.getWidth() / 100,
                raster.getHeight() / 100,
                0.1,
                20.
        );

        cube = new Cube();
        pyramid = new Pyramid();
        hexagone = new Hexagone();

        hexagone.setModel(hexagone.getModel().mul(new Mat4Transl(1.5,0,0)));

        axisX = new AxisX();
        axisY = new AxisY();
        axisZ = new AxisZ();

        scene.add(cube);
        scene.add(hexagone);
        scene.add(pyramid);


        currentObjectIndex = 0;
        currentObject = scene.get(currentObjectIndex);
    }

    public void renderScene() {
        clear(0x000000);

        wiredRenderer.setView(camera.getViewMatrix());
        if(orth){
            wiredRenderer.setProj(projectionOrth);
        }else{
            wiredRenderer.setProj(projection);
        }

        wiredRenderer.render(raster, axisX, 0xff0000);
        wiredRenderer.render(raster, axisY, 0x00ff00);
        wiredRenderer.render(raster, axisZ, 0x0000ff);
        wiredRenderer.renderScene(raster, scene, currentObject);

        panel.repaint();
    }

    public void clear(int color) {
        raster.clear(color);
    }

    public void start() {
        renderScene();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Canvas(1000, 800).start());
    }

}
