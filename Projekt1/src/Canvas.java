import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Optional;

import rasterOp.Fill.SeedFill;
import rasterOp.LineRasterizerBresenham;
import rasterOp.LineRasterizerMidpoint;
import rasterData.RasterBI;
import model.Point;
import model.Line;
import rasterOp.Liner;
import model.Polygon;
import rasterOp.PolygonRasterizer;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class Canvas{

    private JFrame frame;
    private JPanel panel;
    private RasterBI img;

    private int startX, startY;
    private Point start;

    private ArrayList<Line> lines = new ArrayList<>();

    boolean editLine = false;
    boolean snapToGrid = false;
    boolean createPoligon = false;
    boolean fillPlane = false;

    ArrayList<Point> polygonArray = new ArrayList<>();
    Polygon polygon = new Polygon(polygonArray);

    public Canvas(int width, int height) {
        frame = new JFrame();

        frame.setLayout(new BorderLayout());
        frame.setTitle("UHK FIM PGRF : " + this.getClass().getName());
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // set pixel settings, witdth, height and rgb
        img = new RasterBI(width, height);

        panel = new JPanel() {
            private static final long serialVersionUID = 1L;

            // inicialize default graphics in panel
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                img.present(g);
            }
        };
        panel.setPreferredSize(new Dimension(width, height));
        frame.add(panel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
        panel.requestFocusInWindow();

        Liner lineRaster = new LineRasterizerBresenham();
        SeedFill seedFill = new SeedFill();

        panel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch(e.getKeyCode()){
                    case KeyEvent.VK_C:
                        img.clear(0x000000);
                        panel.repaint();
                        lines.clear();
                        break;
                    case KeyEvent.VK_E:
                        if(editLine == false && lines.size() > 0){
                            System.out.println("edit is true");
                            editLine = true;
                        }else{
                            System.out.println("edit is false");
                            editLine = false;
                        }
                        break;
                    case KeyEvent.VK_SHIFT:

                        if(snapToGrid == false){
                            System.out.println("snap enabled");
                            snapToGrid = true;
                        }else{
                            System.out.println("snap disabled");
                            snapToGrid = false;
                        }
                        break;
                    case KeyEvent.VK_P:

                        if(createPoligon == false){
                            System.out.println("Poligon enabled");
                            createPoligon = true;
                        }else{
                            System.out.println("Poligon disabled");
                            createPoligon = false;
                        }
                        break;
                    case KeyEvent.VK_F:
                        if(fillPlane == false){
                            System.out.println("fillPlane enabled");
                            fillPlane = true;
                        }else{
                            System.out.println("fillPlane disabled");
                            fillPlane = false;
                        }
                        break;
                }
            }
        });

        PolygonRasterizer polygonRasterizer = new PolygonRasterizer();

        panel.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e){
                if(createPoligon){
                    img.clear(0x000000);
                    lineRaster.redrawLines(img, lines);
                    startX = e.getX();
                    startY = e.getY();

                    start = new Point(startX, startY);

                    polygon.addPoint(start);

                    polygonRasterizer.drawPolygon(img, polygon);
                    panel.repaint();
                }
                if(fillPlane){
                    seedFill.fill4(img, panel, new Point(e.getX(), e.getY()));

                    panel.repaint();
                }
            }
            @Override
            public void mousePressed(MouseEvent e) {
                    startX = e.getX();
                    startY = e.getY();

                    start = new Point(startX, startY);

                if(editLine){
                    Optional<Point> point = start.getClosestEndpointInRadius(img, lineRaster);

                    if(point.isPresent()){
                        Point startPoint = point.get();

                        int x = startPoint.x;
                        int y = startPoint.y;

                        start = new Point(x, y);
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e){
                if(!createPoligon){
                    Point end = new Point(e.getX(), e.getY());
                    Line line = new Line(start, end);

                    if(snapToGrid == true){
                        Point x = line.returnAngledPoint();
                        end = x;
                        line = new Line(start, end);
                    }

                    lines.add(line);

                    lineRaster.redrawLines(img, lines);
                    polygonRasterizer.drawPolygon(img, polygon);

                    panel.repaint();
                }
            }
        });

        panel.addMouseMotionListener(new MouseMotionAdapter() {


            @Override
            public void mouseDragged(MouseEvent e){

                if(!createPoligon){
                    img.clear(0x000000);
                    Point end = new Point(e.getX(), e.getY());
                    Line line = new Line(start, end);

                    if(snapToGrid == true){
                        Point x = line.returnAngledPoint();
                        end = x;
                        line = new Line(start, end);
                    }

                    lineRaster.drawLine(img, line, 8);

                    lineRaster.redrawLines(img, lines);
                    polygonRasterizer.drawPolygon(img, polygon);

                    panel.repaint();
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Canvas(800, 600));
    }

}