import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class CanvasLine {

    private JFrame frame;
    private JPanel panel;
    private BufferedImage img;

    public CanvasLine(int width, int height) {
        frame = new JFrame();

        frame.setLayout(new BorderLayout());
        frame.setTitle("UHK FIM PGRF : " + this.getClass().getName());
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // set pixel settings, witdth, height and rgb
        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        panel = new JPanel() {
            private static final long serialVersionUID = 1L;

            // inicialize default graphics in panel
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                present(g);
            }
        };

        panel.setPreferredSize(new Dimension(width, height));

        frame.add(panel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);

        panel.requestFocusInWindow();
    }


    // clears panel
    public void clear() {
        Graphics gr = img.getGraphics();
        gr.setColor(new Color(0x2f2f2f));
        gr.fillRect(0, 0, img.getWidth(), img.getHeight());
    }

    // inicialize panel ill draw on
    // it also updates existing panel
    public void present(Graphics graphics) {
        graphics.drawImage(img, 0,0, null);
    }

    // draw pixels on panel
    public void draw(int x,int y, int rgb) {
        img.setRGB(x, y, rgb);
    }

    public void midPoint(int x1, int y1, int x2, int y2){
        int sx=(x1+x2)/2;
        int sy=(y1+y2)/2;
        draw(sx,sy,0xff00ff);
        for(int i = 0; i < 20; i++){
            draw(sx-i,sy-i,0xff00ff);
        }
        if ((Math.abs(x1-sx)>1) || (Math.abs(y1-sy)>1))
            midPoint(x1,y1,sx,sy);
        if ((Math.abs(x2-sx)>1) || (Math.abs(y2-sy)>1))
            midPoint(sx,sy,x2,y2);
    }

    // start function
    public void start() {
        int x = img.getWidth()/2;
        int y = img.getHeight()/2;
        midPoint(x, y, 12, 12);
        panel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CanvasLine(800, 600).start());
    }

}
