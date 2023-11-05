import Utils.BooleanUtil;
import Utils.ButtonUtil;
import Utils.ColorUtil;
import model.Line;
import model.Point;
import model.Polygon;
import rasterData.RasterBI;
import rasterOp.Fill.SeedFill;
import rasterOp.LineRasterizers.LineRasterizerBresenham;
import rasterOp.LineRasterizers.Liner;
import rasterOp.PolygonRasterizers.PolygonRasterizer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.Serial;
import java.util.ArrayList;
import java.util.Optional;

public class Canvas {

    private final JPanel panel;
    private final RasterBI img;
    private final ArrayList<Line> lines = new ArrayList<>();
    boolean editLine = false;
    boolean snapToGrid = false;
    boolean createPoligon = false;
    boolean fillPlane = false;
    ArrayList<Point> polygonArray = new ArrayList<>();
    Polygon polygon = new Polygon(polygonArray);
    BooleanUtil booleanUtil = new BooleanUtil();
    ButtonUtil buttonUtil = new ButtonUtil();
    private int startX, startY;
    private Point start;

    public Canvas(int width, int height) {
        JFrame frame = new JFrame();

        frame.setLayout(new BorderLayout());
        frame.setTitle("UHK FIM PGRF : " + this.getClass().getName());
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        // set pixel settings, witdth, height and rgb
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

        Liner lineRaster = new LineRasterizerBresenham();
        SeedFill seedFill = new SeedFill();

        //inicializing all buttons
        JButton clearButton = buttonUtil.createNewButton("clear", width, 20);
        JButton editLineButton = buttonUtil.createNewButton("edit line", width, 20);
        JButton snapButton = buttonUtil.createNewButton("snap tool", width, 20);
        JButton poligonButton = buttonUtil.createNewButton("poligon", width, 20);
        JButton fillButton = buttonUtil.createNewButton("fill", width, 20);

        panel.add(clearButton);
        panel.add(editLineButton);
        panel.add(snapButton);
        panel.add(poligonButton);
        panel.add(fillButton);

        clearButton.addActionListener(e -> {
            img.clear(0x000000);
            panel.repaint();
            lines.clear();
            polygonArray.clear();
        });

        editLineButton.addActionListener(e -> editLine = !editLine);

        snapButton.addActionListener(e -> snapToGrid = booleanUtil.switchValue(snapToGrid));

        poligonButton.addActionListener(e -> {
            createPoligon = booleanUtil.switchValue(createPoligon);
            fillPlane = false;
        });

        fillButton.addActionListener(e -> {
            fillPlane = booleanUtil.switchValue(fillPlane);
            createPoligon = false;
        });

        PolygonRasterizer polygonRasterizer = new PolygonRasterizer();

        panel.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (createPoligon) {
                    img.clear(0x000000);
                    lineRaster.redrawLines(img, lines);
                    startX = e.getX();
                    startY = e.getY();

                    start = new Point(startX, startY);

                    polygon.addPoint(start);

                    polygonRasterizer.drawPolygon(img, polygon);
                    panel.repaint();
                }
                if (fillPlane) {
                    ColorUtil colorUtil = new ColorUtil();
                    int randomColor = colorUtil.getRandomColor();

                    seedFill.fill4(img, panel, new Point(e.getX(), e.getY()), randomColor);

                    panel.repaint();
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                startX = e.getX();
                startY = e.getY();

                start = new Point(startX, startY);

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
                    polygonRasterizer.drawPolygon(img, polygon);

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