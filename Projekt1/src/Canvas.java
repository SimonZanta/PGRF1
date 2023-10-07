import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import rasterData.LineRasterTrivial;
import rasterData.LineRasterizerMidpoint;
import rasterData.RasterBI;
import model.Point;
import model.Line;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class Canvas{

    private JFrame frame;
    private JPanel panel;
    private RasterBI img;

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

        panel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch(e.getKeyCode()){
                    case KeyEvent.VK_UP:
                        break;
                }
            }
        });

        panel.addMouseListener(new MouseAdapter() {

            int startX, startY;
            Point start;
            @Override
            public void mousePressed(MouseEvent e) {
                startX = e.getX();
                startY = e.getY();

                start = new Point(startX, startY);
            }

            @Override
            public void mouseReleased(MouseEvent e){

                    System.out.println("xddddd");
                    Point end = new Point(e.getX(), e.getY());

                    Line line = new Line(start, end);

                    System.out.println(line.getX1() + " " + line.getY1() + " " + line.getX2() + " " + line.getY2());

                    LineRasterizerMidpoint lineRaster = new LineRasterizerMidpoint();

                    lineRaster.Midpoint(img, line);

                    panel.repaint();
            }
        });
    }

    // start function
    public void start() {
        int x = img.getWidth()/2;
        int y = img.getHeight()/2;
        Point point1 = new Point(x, y);
        Point point2 = new Point(450, 250);
        Point point3 = new Point(550, 1);
        Point point4 = new Point(600, 1);

        Line line1 = new Line(point1, point4);
        Line line2 = new Line(point1, point3);

        LineRasterTrivial trivial = new LineRasterTrivial();
        LineRasterizerMidpoint midpoint = new LineRasterizerMidpoint();

        /*trivial.LineRasterTrivial(img, line1);
        trivial.LineRasterTrivial(img, line2);*/

        midpoint.Midpoint(img, line1);
        midpoint.Midpoint(img, line2);

        /*midpoint.Midpoint(img);*/

        /*point1.Draw(img);*/
        panel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Canvas(800, 600).start());
    }

}