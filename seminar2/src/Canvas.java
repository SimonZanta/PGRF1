import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import rasterData.RasterBI;
import objectdata.Point;
import rasterop.LinerTrivial;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class Canvas{

    private JFrame frame;
    private JPanel panel;
    private RasterBI img;

    double startX, startY;

    Point start;

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

        panel.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                startX = e.getX();
                startY = e.getY();

                start = new Point(startX, startY);
            }
        });

        panel.addMouseMotionListener(new MouseMotionAdapter() {


            @Override
            public void mouseDragged(MouseEvent e){
                img.clear(0x000000);

                Point end = new Point(e.getX(), e.getY());
                LinerTrivial lineRaster = new LinerTrivial();

                lineRaster.drawline(img, startX, end.getX(), startY, end.getY(), 0xff0000);

                panel.repaint();
            }
        });
    }


    // inicialize panel ill draw on
    // it also updates existing panel
/*    public void present(Graphics graphics) {
      graphics.drawImage(img, 0,0, null);
}*/

    // draw pixels on panel
    public void draw(int x,int y, int rgb) {
        img.setColor(x, y, rgb);
    }

    // start function
    public void start() {
        int x = img.getWidth()/2;
        int y = img.getHeight()/2;
        draw(x, y, 0xffffff);
        panel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Canvas(800, 600).start());
    }

}