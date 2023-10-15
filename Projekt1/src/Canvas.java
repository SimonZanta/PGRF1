import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Optional;

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

    Boolean editLine = false;

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
                        lines.clear();
                        break;
                }
                switch(e.getKeyCode()){
                    case KeyEvent.VK_E:
                        if(editLine == false && lines.size() > 0){
                            System.out.println("edit is true");
                            editLine = true;
                        }else{
                            System.out.println("edit is false");
                            editLine = false;
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
                    Optional<Point> point = isInRadius(start);


                    Point startPoint = point.get();

                    int x = startPoint.x;
                    int y = startPoint.y;

                    start = new Point(x, y);
                }
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

    public Optional<Point> isInRadius(Point point){

        for(int i = 0; i < lines.size(); i++){

            double radiusFirst = Math.sqrt(Math.pow((lines.get(i).getFirst().x - point.x), 2) + Math.pow((lines.get(i).getFirst().y - point.y), 2));
            double radiusSecond = Math.sqrt(Math.pow((lines.get(i).getSecond().x - point.x), 2) + Math.pow((lines.get(i).getSecond().y - point.y), 2));

            Point firstPoint = lines.get(i).getFirst();
            Point secondPoint = lines.get(i).getSecond();

            if(radiusFirst <= 20 || radiusSecond <= 20){

                img.clear(0x000000);

                lines.remove(i);

                LineRasterizerMidpoint lineRaster = new LineRasterizerMidpoint();

                for(int d = 0; d < lines.size(); d++){
                    lineRaster.Midpoint(img, lines.get(d));
                }

                panel.repaint();

                if(radiusFirst <= 20){
                    return Optional.of(secondPoint);
                }else if(radiusSecond <= 20){
                    return Optional.of(firstPoint);
                }
            }
        }
        return Optional.empty();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Canvas(800, 600));
    }

}