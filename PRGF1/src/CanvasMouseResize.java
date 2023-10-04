
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * trida pro kresleni na platno: resizovani platna se zachovanim puvodniho obrazu
 * 
 * @author PGRF FIM UHK
 * @version 2020
 */

public class CanvasMouseResize {

	private JFrame frame;
	private JPanel panel;
	private BufferedImage img;

	public CanvasMouseResize(int width, int height) {
		frame = new JFrame();

		frame.setLayout(new BorderLayout());
		frame.setTitle("UHK FIM PGRF : " + this.getClass().getName());
		frame.setResizable(true);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		panel = new JPanel() {
			private static final long serialVersionUID = 1L;

			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				present(g);
			}
		};
		panel.setPreferredSize(new Dimension(width, height));

		frame.add(panel, BorderLayout.CENTER);
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);

		panel.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				BufferedImage newImg = new BufferedImage(panel.getWidth(), panel.getHeight(),
						BufferedImage.TYPE_INT_RGB);
				img = newImg;
				draw();
			}
		});

		panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1)
					img.setRGB(e.getX(), e.getY(), 0xff0000);
				if (e.getButton() == MouseEvent.BUTTON2)
					img.setRGB(e.getX(), e.getY(), 0xff00);
				if (e.getButton() == MouseEvent.BUTTON3)
					img.setRGB(e.getX(), e.getY(), 0xff);
				panel.repaint();
			}
		});
	}

	public void clear() {
		Graphics gr = img.getGraphics();
		gr.setColor(new Color(0x2f2f2f));
		gr.fillRect(0, 0, img.getWidth(), img.getHeight());
	}

	public void present(Graphics graphics) {
		graphics.drawImage(img, 0, 0, null);
	}

	public void draw() {
		clear();
		img.setRGB(10, 10, 0xffff00);
		img.getGraphics().drawString("Resize the window", 5, img.getHeight() - 5);

	}

	public void start() {
		draw();
		panel.repaint();
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new CanvasMouseResize(800, 600).start());
	}

}
