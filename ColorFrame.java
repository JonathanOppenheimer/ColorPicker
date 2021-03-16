import javax.swing.JFrame;
import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class ColorFrame extends JFrame {

	public static void main(String[] args) {

		// sets up frame
		JFrame frame = new JFrame();
		if (System.getProperty("os.name").startsWith("Windows")) {
			frame.setBounds(10, 10, 400, 320);
		} else {
			frame.setBounds(10, 10, 400, 300);
		}
		frame.setLocationRelativeTo(null);
		frame.setTitle("Color Picker");
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// adds panel
		currentColor colorPanel = new currentColor();
		frame.add(colorPanel);
		infoBox("Welcome to Jonathan Oppenheimer's Color Picker Application, " + "\n"
				+ "developed for the IB HL Computer Science II Course. " + "\n" + "\n"
				+ "To use this application, move your mouse around the screen " + "\n"
				+ "and the application will show you where your mouse is, " + "\n"
				+ "and the color values for the pixel highlighted by red." + "\n" + "\n"
				+ "To save the current pixel, simply press space, and the " + "\n"
				+ "color will be saved to the 2x5 grid of saved colors." + "\n"
				+ "The box highlighted in yellow will be the next box to be" + "\n" + "saved to. Press OK to continue.",
				"Instructions");

		// paints frame
		frame.setVisible(true);
		while (true) {
			colorPanel.repaint();
		}
	}

	// used to show a pop up box of instructions before use
	public static void infoBox(String infoMessage, String titleBar) {
		JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
	}
}