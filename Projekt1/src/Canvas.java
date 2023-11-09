import Utils.BooleanUtil;
import Utils.ButtonUtil;
import model.Line;
import model.Point;
import model.Polygon;
import rasterData.RasterBI;
import rasterOp.Fill.ScanLine;
import rasterOp.Fill.SeedFill;
import rasterOp.LineRasterizers.LineRasterizerBresenham;
import rasterOp.LineRasterizers.Liner;
import rasterOp.PolygonRasterizers.ElipseRasterizer;
import rasterOp.PolygonRasterizers.PolygonRasterizer;
import rasterOp.PolygonRasterizers.RecrangleRasterizer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.Serial;
import java.util.ArrayList;
import java.util.Optional;

public class Canvas {

    // panel global variables
    private final JPanel panel;
    private final RasterBI img;


    // button variables
    ButtonUtil buttonUtil = new ButtonUtil();
    BooleanUtil booleanUtil = new BooleanUtil();
    boolean editLine = false;
    boolean snapToGrid = false;
    boolean createPoligon = false;
    boolean fillPlane = false;
    boolean createRectangle = false;


    // line variables
    private Point start;
    private int lineStartX, lineStartY;
    private final ArrayList<Line> lines = new ArrayList<>();


    // polygon variables
    ArrayList<Point> polygonArray = new ArrayList<>();
    Polygon polygon = new Polygon(polygonArray);


    // rectangle variables
    private final ArrayList<Polygon> rectangles = new ArrayList<>();
    private Point rectangleStart;
    private int rectangleCounter = 0;


    // elipse variables


    public Canvas(int width, int height) {
        JFrame frame = new JFrame();

        frame.setLayout(new BorderLayout());
        frame.setTitle("UHK FIM PGRF : " + this.getClass().getName());
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        // RBI inicialization - set draw plane width and height
        img = new RasterBI(width - 150, height);

        panel = new JPanel() {
            @Serial
            private static final long serialVersionUID = 1L;

            // inicialize default graphics in panel
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                img.present(g);
            }
        };
        panel.setPreferredSize(new Dimension(width, height));
        panel.setLayout(null);

        frame.add(panel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
        panel.requestFocusInWindow();


        // rasterizes inicialization - needs to be after RBI inicialization
        Liner lineRaster = new LineRasterizerBresenham();
        SeedFill seedFill = new SeedFill();

        //inicializing all buttons
        JButton clearButton = buttonUtil.createNewButton("clear", width, 20);
        JButton editLineButton = buttonUtil.createNewButton("edit line", width, 20);
        JButton snapButton = buttonUtil.createNewButton("snap tool", width, 20);
        JButton poligonButton = buttonUtil.createNewButton("poligon", width, 20);
        JButton fillButton = buttonUtil.createNewButton("fill", width, 20);
        JButton rectangleButton = buttonUtil.createNewButton("rectangle", width, 20);

        panel.add(clearButton);
        panel.add(editLineButton);
        panel.add(snapButton);
        panel.add(poligonButton);
        panel.add(fillButton);
        panel.add(rectangleButton);

        clearButton.addActionListener(e -> {
            img.clear(0x000000);
            panel.repaint();
            lines.clear();
            polygonArray.clear();
        });

        editLineButton.addActionListener(e -> editLine = booleanUtil.switchValue(editLine));

        snapButton.addActionListener(e -> snapToGrid = booleanUtil.switchValue(snapToGrid));

        rectangleButton.addActionListener(e -> {createRectangle = true; rectangleCounter = 0;});

        poligonButton.addActionListener(e -> {
            createPoligon = booleanUtil.switchValue(createPoligon);
            fillPlane = false;
        });

        fillButton.addActionListener(e -> {
            fillPlane = booleanUtil.switchValue(fillPlane);
            createPoligon = false;
        });

        PolygonRasterizer polygonRasterizer = new PolygonRasterizer();
        RecrangleRasterizer recrangleRasterizer = new RecrangleRasterizer();


        // panel listeners
        panel.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                ElipseRasterizer elipseRasterizer = new ElipseRasterizer();
                elipseRasterizer.drawTest(img, e.getX(), e.getY());
                if (createPoligon) {
                    img.clear(0x000000);
                    lineRaster.redrawLines(img, lines);
                    lineStartX = e.getX();
                    lineStartY = e.getY();

                    start = new Point(lineStartX, lineStartY);

                    polygon.addPoint(start);

                    polygonRasterizer.draw(img, polygon);
                    panel.repaint();
                }
                if (fillPlane) {
                    /*ColorUtil colorUtil = new ColorUtil();
                    int randomColor = colorUtil.getRandomColor();*/
                    ScanLine scanLine = new ScanLine(polygonArray);
                    scanLine.fill(img);

                    /*seedFill.fill(img, panel, new Point(e.getX(), e.getY()), randomColor);*/

                    panel.repaint();
                }
                if(createRectangle){

                    if(rectangleCounter == 0){
                        rectangleStart = new Point(e.getX(), e.getY());
                        rectangleCounter++;
                    }else if(rectangleCounter == 1){
                       Point rectangleEnd = new Point(e.getX(), e.getY());
                       ArrayList<Point> rectangleArray = new ArrayList<>();
                       rectangleArray.add(rectangleStart);
                       rectangleArray.add(rectangleEnd);
                       Polygon rectangle = new Polygon(rectangleArray);

                       rectangles.add(rectangle);
                       rectangleCounter = 0;
                    }

                    recrangleRasterizer.redrawAll(img, rectangles);
                    panel.repaint();
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                lineStartX = e.getX();
                lineStartY = e.getY();

                start = new Point(lineStartX, lineStartY);

                if (editLine) {
                    Optional<Point> point = start.getClosestEndpointInRadius(img, lineRaster, lines);

                    if (point.isPresent()) {
                        Point startPoint = point.get();

                        int x = startPoint.x;
                        int y = startPoint.y;

                        start = new Point(x, y);
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (!createPoligon) {
                    Point end = new Point(e.getX(), e.getY());
                    Line line = new Line(start, end);

                    if (snapToGrid) {
                        end = line.returnAngledPoint();
                        line = new Line(start, end);
                    }

                    lines.add(line);

                    lineRaster.redrawLines(img, lines);
                    polygonRasterizer.draw(img, polygon);
                    recrangleRasterizer.redrawAll(img, rectangles);

                    panel.repaint();
                }
            }
        });

        panel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {

                if (!createPoligon) {
                    img.clear(0x000000);
                    Point end = new Point(e.getX(), e.getY());
                    Line line = new Line(start, end);

                    if (snapToGrid) {
                        end = line.returnAngledPoint();
                        line = new Line(start, end);
                    }

                    lineRaster.drawLine(img, line, 8);

                    lineRaster.redrawLines(img, lines);
                    polygonRasterizer.draw(img, polygon);
                    recrangleRasterizer.redrawAll(img, rectangles);

                    panel.repaint();
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Canvas(800, 600));
    }

}