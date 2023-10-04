
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * trida pro kresleni na platno: zobrazeni pixelu, ovladani klavesnici
 * 
 * @author PGRF FIM UHK
 * @version 2020
 */

public class CanvasKey {

	private JFrame frame;
	private JPanel panel;
	private BufferedImage img;
	private int x, y;

	public CanvasKey(int width, int height) {
		frame = new JFrame();

		frame.setLayout(new BorderLayout());
		frame.setTitle("UHK FIM PGRF : " + this.getClass().getName());
		frame.setResizable(false);
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
		frame.pack();
		frame.setVisible(true);

		panel.requestFocus();
		panel.requestFocusInWindow();
		panel.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_LEFT:
					x--;
					break;
				case KeyEvent.VK_RIGHT:
					x++;
					break;
				case KeyEvent.VK_UP:
					y--;
					break;
				case KeyEvent.VK_DOWN:
					y++;
					break;
				}
				draw();
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

	private void draw() {
		img.setRGB(x, y, 0xffff00);
		System.out.println("[" + x + "," + y + "]");
	}

	public void start() {
		x = img.getWidth() / 2;
		y = img.getHeight() / 2;
		clear();
		draw();
		img.getGraphics().drawString("Use arrow keys", 5, img.getHeight() - 5);
		panel.repaint();
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new CanvasKey(800, 600).start());
	}

}
