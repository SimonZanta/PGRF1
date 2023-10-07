import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * trida pro kresleni na platno: zobrazeni pixelu
 * 
 * @author PGRF FIM UHK
 * @version 2020
 */

public class Canvas {

	private JFrame frame;
	private JPanel panel;
	private BufferedImage img;

	private int x = 0;
	private int y = 0;

	private int rgbDefault = 0xffff00;

	private int crossDiameter = 10;

	int lastPressedButton = 0;

	int colorTest = 0x00ff00;
	int colorBackground = 0x000000;

	List<Integer> pixelCoordinates = new ArrayList<>();

	public Canvas(int width, int height) {
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

		panel.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {

				int rgb = rgbDefault;

				switch(e.getKeyCode()){
					case KeyEvent.VK_UP:
						y--;
						draw(x, y, rgb);
						deletePixelUp(x, y, rgb);
						break;
					case KeyEvent.VK_DOWN:
						y++;
						draw(x, y, rgb);
						deletePixelDown(x, y, rgb);
						break;
					case KeyEvent.VK_LEFT:
						x--;
						/*rgb = 0xff0000;*/
						draw(x, y, rgb);
						deletePixelLeft(x, y, rgb);
						break;
					case KeyEvent.VK_RIGHT:
						x++;
						/*rgb = 0xff0000;*/
						draw(x, y, rgb);
						deletePixelRight(x, y, rgb);
						break;
				}

				System.out.println("x:y = " + x + ":" + y);
				System.out.println("calculated coordinates: " + calcPxCoordinates(x, y));
				pixelCoordinates.add(calcPxCoordinates(x, y));

				// refactor
				if(lastPressedButton != e.getKeyCode()){
					if(lastPressedButton != KeyEvent.VK_UP && e.getKeyCode() == KeyEvent.VK_DOWN){
						deletePixelChangeDirDown(x, y, rgb);
					} else if (lastPressedButton != KeyEvent.VK_RIGHT && e.getKeyCode() == KeyEvent.VK_LEFT) {
						deletePixelChangeDirRight(x, y, rgb);
					}
					else if (lastPressedButton != KeyEvent.VK_DOWN && e.getKeyCode() == KeyEvent.VK_UP) {
						deletePixelChangeDirUP(x, y, rgb);
					}
					else if (lastPressedButton != KeyEvent.VK_LEFT && e.getKeyCode() == KeyEvent.VK_RIGHT) {
						deletePixelChangeDirLeft(x, y, rgb);
					}
				}

					lastPressedButton = e.getKeyCode();

					System.out.println(lastPressedButton);

				// calling present updates whole panel
				// without this nothing will move
					present(panel.getGraphics());
			}
		});
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
		// clear will make cursor just move
		// comment clear to draw
		/*clear();*/

		img.setRGB(x, y, rgb);
		img.setRGB(x - crossDiameter, y, rgb);
		img.setRGB(x + crossDiameter, y, rgb);
		img.setRGB(x, y - crossDiameter, rgb);
		img.setRGB(x, y + crossDiameter, rgb);

	}

	public int calcPxCoordinates(int x, int y){
		return (x * 1000) + y;
	}

	//refactor?
	public void deletePixelUp(int x,int y, int rgb) {

		if(!pixelCoordinates.contains(calcPxCoordinates(x - crossDiameter,y+1))){
			img.setRGB( x - crossDiameter, y+1, colorBackground);
		}
		if(!pixelCoordinates.contains(calcPxCoordinates(x + crossDiameter,y+1))) {
			img.setRGB(x + crossDiameter, y+1, colorBackground);
		}
	}
	public void deletePixelDown(int x,int y, int rgb) {
		if(!pixelCoordinates.contains(calcPxCoordinates(x - crossDiameter,y-1))) {
			img.setRGB(x - crossDiameter, y-1, colorBackground);
		}
		if(!pixelCoordinates.contains(calcPxCoordinates(x + crossDiameter,y-1))) {
			img.setRGB(x + crossDiameter, y-1, colorBackground);
		}
	}
	public void deletePixelLeft(int x,int y, int rgb) {
		if(!pixelCoordinates.contains(calcPxCoordinates(x+1, y - crossDiameter))) {
			img.setRGB(x+1, y - crossDiameter, colorBackground);
		}
		if(!pixelCoordinates.contains(calcPxCoordinates(x+1, y + crossDiameter))) {
			img.setRGB(x+1, y + crossDiameter, colorBackground);
		}
	}
	public void deletePixelRight(int x,int y, int rgb) {
		if(!pixelCoordinates.contains(calcPxCoordinates(x-1, y - crossDiameter))) {
			img.setRGB(x-1, y - crossDiameter, colorBackground);
		}
		if(!pixelCoordinates.contains(calcPxCoordinates(x-1, y + crossDiameter))) {
			img.setRGB(x-1, y + crossDiameter, colorBackground);
		}
	}

	public void deletePixelChangeDirDown(int x,int y, int rgb){
		img.setRGB( x, y - (crossDiameter + 1), colorBackground);
	}
	public void deletePixelChangeDirRight(int x,int y, int rgb){
		img.setRGB( x + (crossDiameter + 1), y, colorBackground);
	}
	public void deletePixelChangeDirLeft(int x,int y, int rgb){
		img.setRGB( x - (crossDiameter + 1), y, colorBackground);
	}
	public void deletePixelChangeDirUP(int x,int y, int rgb){
		img.setRGB( x, y + (crossDiameter + 1), colorBackground);
	}

	// start function
	public void start() {
		x = img.getWidth()/2;
		y = img.getHeight()/2;
		draw(x, y, rgbDefault);
		panel.repaint();
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new Canvas(800, 600).start());
	}

}