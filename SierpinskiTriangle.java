import java.awt.Dimension;

import javax.swing.JFrame;

import java.awt.*;

public class SierpinskiTriangle {

	static JFrame frame;
	public static void main(String[] args) {
		setupFrame();
		drawTriangles();
	}
	
	private static void setupFrame() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		frame=new JFrame("Sierpinski Triangle");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBackground(Color.white);
		frame.setSize(screenSize.width/2, screenSize.height/2);
	}
	
	private static void drawTriangles() {
		frame.add(new SierpinskiPanel());
		frame.setVisible(true);
	}
}
