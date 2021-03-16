import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Stroke;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class currentColor extends JPanel implements KeyListener {

	// creates 9 pixels to track pixels around mouse, an array to store colors in, a
	// counter to see which preset box to fill next and a counter to save which spot
	// in the array the color is saved to
	static Color mouseColor1, mouseColor2, mouseColor3, mouseColor4, mouseColor5, mouseColor6, mouseColor7, mouseColor8,
			mouseColor9;
	Color[] savedColors = new Color[10];
	int colorFillCounter = 0;
	int colorSetCounter = 0;
	Robot robot;

	public currentColor() {
		setLayout(new BorderLayout());
		JPanel southPanel = new JPanel();
		JLabel save = new JLabel("Saved Colors");

		southPanel.add(save);
		add(southPanel, BorderLayout.SOUTH);
		southPanel.setVisible(true);
		setFocusable(true);
		addKeyListener(this);

		// fills array up with white to initialize
		for (int i = 0; i < savedColors.length; i++) {
			savedColors[i] = Color.white;
		}
	}

	// method to return mouse position
	public Point mouseLocation() {
		Point p = MouseInfo.getPointerInfo().getLocation();
		return p;
	}

	public Robot getRobot() throws Exception {
		robot = new Robot();
		return robot;
	}

	// method to return color at a given coordinate
	public Color getPixelColor(int x, int y) throws Exception {
		Robot robot = new Robot();
		return robot.getPixelColor(x, y);
	}

	// method that is constantly invoked to fill the active selector area
	public void setMouseColors() throws Exception {
		mouseColor1 = getPixelColor(mouseLocation().x - 1, mouseLocation().y - 1);
		mouseColor2 = getPixelColor(mouseLocation().x, mouseLocation().y - 1);
		mouseColor3 = getPixelColor(mouseLocation().x + 1, mouseLocation().y - 1);
		mouseColor4 = getPixelColor(mouseLocation().x - 1, mouseLocation().y);
		mouseColor5 = getPixelColor(mouseLocation().x, mouseLocation().y);
		mouseColor6 = getPixelColor(mouseLocation().x + 1, mouseLocation().y);
		mouseColor7 = getPixelColor(mouseLocation().x - 1, mouseLocation().y + 1);
		mouseColor8 = getPixelColor(mouseLocation().x, mouseLocation().y + 1);
		mouseColor9 = getPixelColor(mouseLocation().x + 1, mouseLocation().y + 1);
	}

	// paints within the JFrame
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		try {
			setMouseColors();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//cleans frame depending on frame size
		if (System.getProperty("os.name").startsWith("Windows")) {
			g2.clearRect(0, 0, 400, 320);
		} else {
			g2.clearRect(0, 0, 400, 300);
		}

		// creates box around active selector
		g2.setColor(Color.BLACK);
		float thickness = 4;
		Stroke oldStroke = g2.getStroke();
		g2.setStroke(new BasicStroke(thickness));
		g2.drawRect(18, 18, 94, 94);
		g2.setStroke(oldStroke);

		// paints in pixels created in setMouseColors
		g2.setColor(mouseColor1);
		g2.fillRect(20, 20, 30, 30);
		g2.setColor(mouseColor2);
		g2.fillRect(50, 20, 30, 30);
		g2.setColor(mouseColor3);
		g2.fillRect(80, 20, 30, 30);
		g2.setColor(mouseColor4);
		g2.fillRect(20, 50, 30, 30);
		g2.setColor(mouseColor5);
		g2.fillRect(50, 50, 30, 30);
		g2.setColor(mouseColor6);
		g2.fillRect(80, 50, 30, 30);
		g2.setColor(mouseColor7);
		g2.fillRect(20, 80, 30, 30);
		g2.setColor(mouseColor8);
		g2.fillRect(50, 80, 30, 30);
		g2.setColor(mouseColor9);
		g2.fillRect(80, 80, 30, 30);

		// creates box around the focused pixel
		thickness = 3;
		g2.setStroke(new BasicStroke(thickness));
		g2.setColor(Color.RED);
		g2.drawRect(50, 50, 30, 30);
		thickness = 1;
		g2.setStroke(new BasicStroke(thickness));
		g2.setColor(Color.WHITE);
		g2.drawRect(50, 50, 30, 30);
		thickness = 4;
		g2.setStroke(new BasicStroke(thickness));
		g2.setStroke(oldStroke);

		// sets font
		Font font = new Font("Calibri", Font.BOLD, 18);
		g2.setFont(font);

		g2.setColor(Color.BLACK);
		// draws RGB values
		g2.drawString("RGB: " + mouseColor5.getRed() + ", " + mouseColor5.getGreen() + ", " + mouseColor5.getBlue(),
				120, 30);

		// draws HEX values
		g2.drawString("HEX: "
				+ String.format("#%02X%02X%02X", mouseColor5.getRed(), mouseColor5.getGreen(), mouseColor5.getBlue()),
				120, 60);

		// draws CMYK values
		int[] cmyk = RgbCmykConverter.rgbToCmyk(mouseColor5.getRed(), mouseColor5.getGreen(), mouseColor5.getBlue());
		String stringBuilder = "";
		for (int i = 0; i < cmyk.length; i++) {
			stringBuilder = stringBuilder + ", " + Integer.toString(cmyk[i]);
		}
		stringBuilder = stringBuilder.substring(2);
		g2.drawString("CMYK: " + stringBuilder, 120, 90);

		// draws HSB values
		float[] hsb = Color.RGBtoHSB(mouseColor5.getRed(), mouseColor5.getGreen(), mouseColor5.getBlue(), null);
		stringBuilder = "";
		for (int i = 0; i < hsb.length; i++) {
			stringBuilder = stringBuilder + ", " + String.format("%.4f", hsb[i]);
		}
		stringBuilder = stringBuilder.substring(2);
		g2.drawString("HSB: " + stringBuilder, 120, 120);

		// draws black boxes, and saved colors for preset area
		g2.setStroke(new BasicStroke(thickness));
		if (colorFillCounter > 9) {
			colorFillCounter = 0;
		}
		for (int i = 3; i < 5; i++) {
			for (int j = 1; j < 6; j++) {
				g2.setColor(Color.BLACK);
				g2.drawRect((50 * j) + 25, 50 * i, 50, 50);
				g2.setColor(savedColors[colorFillCounter]);
				colorFillCounter++;
				g2.fillRect((50 * j) + 27, (50 * i) + 2, 46, 46);
			}
		}

		// highlights which preset square will be filled in next
		g2.setColor(Color.YELLOW);
		thickness = 2;
		g2.setStroke(new BasicStroke(thickness));
		if (colorSetCounter < 5) {
			g2.drawRect((50 * colorSetCounter) + 75, 150, 50, 50);
		} else if (colorSetCounter == 10) {
			g2.drawRect(75, 150, 50, 50);
		} else {
			g2.drawRect((50 * colorSetCounter) - 175, 200, 50, 50);
		}

		// rests stroke back to default
		g2.setStroke(oldStroke);
	}

	// method to save the active pixel to the next spot in the 2x5 saved colors area
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			if (colorSetCounter > 9) {
				colorSetCounter = 0;
			}
			try {
				savedColors[colorSetCounter] = getPixelColor(mouseLocation().x, mouseLocation().y);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			colorSetCounter++;
		}

		// set of triggers that will move mouse 1 pixel in accordance with arrow keys --
		// application must have full access to computer for this to work however
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			try {
				getRobot().mouseMove(mouseLocation().x, mouseLocation().y - 1);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			try {
				getRobot().mouseMove(mouseLocation().x, mouseLocation().y + 1);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			try {
				getRobot().mouseMove(mouseLocation().x - 1, mouseLocation().y);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			try {
				getRobot().mouseMove(mouseLocation().x + 1, mouseLocation().y);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}
}