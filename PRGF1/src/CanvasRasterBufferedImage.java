import rasterize.LineRasterizerGraphics;
import rasterize.RasterBufferedImage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * trida pro kresleni na platno: vyuzita tridy RasterBufferedImage
 * 
 * @author PGRF FIM UHK
 * @version 2020
 */

public class CanvasRasterBufferedImage {

	private JPanel panel;
	private RasterBufferedImage raster;
	private int x,y;
	private LineRasterizerGraphics rasterizer;

	public CanvasRasterBufferedImage(int width, int height) {
		JFrame frame = new JFrame();

		frame.setLayout(new BorderLayout());

		frame.setTitle("UHK FIM PGRF : " + this.getClass().getName());
		frame.setResizable(true);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		raster = new RasterBufferedImage(width, height);
		rasterizer = new LineRasterizerGraphics(raster);
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
				for(int i=-size; i<=size; i++)
					for(int j=-size; j<=size; j++)
						raster.setPixel(e.getX()+i, e.getY()+j, color);
				if (e.getButton() == MouseEvent.BUTTON3) {
					rasterizer.drawLine(x,y,e.getX(),e.getY());
					x = e.getX();
					y = e.getY();
				}
				panel.repaint();
			}
		});

		panel.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				raster.setPixel(e.getX(), e.getY(), 0xffff00);
				panel.repaint();
			}
		});

		panel.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				if (panel.getWidth()<1 || panel.getHeight()<1)
					return;
				if (panel.getWidth()<=raster.getWidth() && panel.getHeight()<=raster.getHeight()) //no resize if new one is smaller
					return;
				RasterBufferedImage newRaster = new RasterBufferedImage(panel.getWidth(), panel.getHeight());

				newRaster.draw(raster);
				raster = newRaster;
				rasterizer = new LineRasterizerGraphics(raster);

			}
		});

	}

	public void clear(int color) {
		raster.setClearColor(color);
		raster.clear();
		
	}

	public void present(Graphics graphics) {
		raster.repaint(graphics);
	}

	public void start() {
		clear(0xaaaaaa);
		raster.getGraphics().drawString("Use mouse buttons and try resize the window", 5, 15);
		panel.repaint();
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new CanvasRasterBufferedImage(800, 600).start());
	}

}
