import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Optional;

import rasterOp.LineRasterTrivial;
import rasterOp.LineRasterizerMidpoint;
import rasterData.RasterBI;
import model.Point;
import model.Line;
import rasterOp.Liner;

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

    Boolean editLine = false;
    boolean snapToGrid = false;


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

        Liner lineRaster = new LineRasterizerMidpoint(panel, lines);

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
                }
            }
        });

        panel.addMouseListener(new MouseAdapter() {

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
                Point end = new Point(e.getX(), e.getY());
                Line line = new Line(start, end);

                if(snapToGrid == true){
                    Point x = returnAngledPoint(line);
                    end = x;
                    line = new Line(start, end);
                }

                lines.add(line);

                lineRaster.redrawLines(img);
            }
        });

        panel.addMouseMotionListener(new MouseMotionAdapter() {


            @Override
            public void mouseDragged(MouseEvent e){
                    img.clear(0x000000);
                    Point end = new Point(e.getX(), e.getY());
                    Line line = new Line(start, end);

                    if(snapToGrid == true){
                        Point x = returnAngledPoint(line);
                        end = x;
                        line = new Line(start, end);
                    }

                    lineRaster.drawLine(img, line, 8);

                    lineRaster.redrawLines(img);
            }
        });
    }

    public Point returnAngledPoint(Line line) {
        int x1 = line.getFirst().x;
        int x2 = line.getSecond().x;
        int y1 = line.getFirst().y;
        int y2 = line.getSecond().y;

        int dx = x2 - x1;
        int dy = y2 - y1;

        double angle = Math.atan2(dy, dx);
        double angleDeg = angle * 180 / Math.PI;

        int angleDegRounded = (int) Math.round((angleDeg / 90)) * 90;

        System.out.println(angleDegRounded);

        switch (angleDegRounded){
            case 90:
                return new Point(x1, y2);
            case -90:
                return new Point(x1, y2);
            case 0:
                return new Point(x2, y1);
            case 180:
                return new Point(x2, y1);
        }

        return new Point(x2, y2);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Canvas(800, 600));
    }

}