import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

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

    private int startX, startY;
    private Point start;

    private ArrayList<Line> lines = new ArrayList<>();

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
                    case KeyEvent.VK_C:
                        img.clear(0x000000);
                        panel.repaint();
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
            }

            @Override
            public void mouseReleased(MouseEvent e){

                    Point end = new Point(e.getX(), e.getY());
                    Line line = new Line(start, end);

                    lines.add(line);

            }
        });

        panel.addMouseMotionListener(new MouseMotionAdapter() {

            @Override
            public void mouseDragged(MouseEvent e){

                img.clear(0x000000);

                Point end = new Point(e.getX(), e.getY());
                Line line = new Line(start, end);
                LineRasterizerMidpoint lineRaster = new LineRasterizerMidpoint();
                lineRaster.Midpoint(img, line);

                for(int i = 0; i < lines.size(); i++){
                    lineRaster.Midpoint(img, lines.get(i));
                }

                panel.repaint();
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Canvas(800, 600));
    }

}