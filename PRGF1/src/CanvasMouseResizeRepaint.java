
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;

import javax.swing.*;

/**
 * trida pro kresleni na platno: zobrazeni pixelu, ovladani mysi
 * 
 * @author PGRF FIM UHK
 * @version 2020
 */

public class CanvasMouseResizeRepaint {

	private JPanel panel;
	private BufferedImage img;

	public CanvasMouseResizeRepaint(int width, int height) {
		JFrame frame = new JFrame();

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
		frame.pack();
		frame.setVisible(true);

		panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				int size = 2;
				int color  = 0xFFFFFF;
				if (e.getButton() == MouseEvent.BUTTON1)
					color = 0xff0000;
				if (e.getButton() == MouseEvent.BUTTON2)
					color = 0xff00;
				if (e.getButton() == MouseEvent.BUTTON3)
					color =0xff;
				for(int i=-size; i<=size; i++)
					for(int j=-size; j<=size; j++)img.setRGB(e.getX()+i, e.getY()+j, color);
			panel.repaint();
			}
		});

		panel.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				img.setRGB(e.getX(), e.getY(), 0xffff00);
				panel.repaint();
			}
		});

		panel.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				if (panel.getWidth()<1 || panel.getHeight()<1)
					return;
				if (panel.getWidth()<=img.getWidth() && panel.getHeight()<=img.getHeight()) //no resize if new is smaller
					return;
				BufferedImage newImg = new BufferedImage(panel.getWidth(), panel.getHeight(), BufferedImage.TYPE_INT_RGB);
				Graphics gr = newImg.getGraphics();
				gr.setColor(new Color(0xaaaaaa));
				gr.fillRect(0, 0, newImg.getWidth(), newImg.getHeight());
				newImg.getGraphics().drawImage(img, 0, 0, null);
				img = newImg;
			}
		});

	}

	public void clear(int color) {
		Graphics gr = img.getGraphics();
		gr.setColor(new Color(color));
		gr.fillRect(0, 0, img.getWidth(), img.getHeight());
		
	}

	public void present(Graphics graphics) {
		graphics.drawImage(img, 0, 0, null);
	}

	public void start() {
		clear(0xaaaaaa);
		img.getGraphics().drawString("Use mouse buttons and try resize the window", 5, 15);
		panel.repaint();
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new CanvasMouseResizeRepaint(800, 600).start());
	}

}
