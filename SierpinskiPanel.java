import java.awt.Color;
import java.awt.Graphics;
import java.awt.Panel;
import java.awt.Polygon;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class SierpinskiPanel extends Panel {
	private int levels = 8;
	private Graphics g;
	private Color[] colors = {new Color(0x172a3a), new Color(0x004346), new Color(0x383293), new Color(0xabcdef), new Color(0xFFFF00), new Color(0xFFF100), new Color(0x03FF00), new Color(0xFFFF15), new Color(0xFF00FF), new Color(0x00FF00), new Color(0xFFF000), new Color(0xFF0100), new Color(0xFF4700)};
	
	/**
	 * Creates three new triangles
	 * @param x
	 * @param y
	 * @return a map with all three triangles
	 */
	private Map<Integer, int[][]> getNewTriangles(int[] x,int[] y){
		Map<Integer, int[][]> triangleMap = new HashMap<>();
		int x01 = (x[0]+x[1])/2;
		int x12 = (x[1]+x[2])/2;
		int x02 = (x[0]+x[2])/2;
		
		int y01 = (y[0]+y[1])/2;
		int y12 = (y[1]+y[2])/2;
		int y02 = (y[0]+y[2])/2;
		
		int[][] coordinates0 = new int[3][3];
		coordinates0[0][0] = x[0];
		coordinates0[0][1] = y[0];
		coordinates0[1][0] = x01;
		coordinates0[1][1] = y01;
		coordinates0[2][0] = x02;
		coordinates0[2][1] = y02;

		int[][] coordinates1 = new int[3][3];
		coordinates1[0][0] = x[1];
		coordinates1[0][1] = y[1];
		coordinates1[1][0] = x01;
		coordinates1[1][1] = y01;
		coordinates1[2][0] = x12;
		coordinates1[2][1] = y12;
		
		int[][] coordinates2 = new int[3][3];
		coordinates2[0][0] = x[2];
		coordinates2[0][1] = y[2];
		coordinates2[1][0] = x02;
		coordinates2[1][1] = y02;
		coordinates2[2][0] = x12;
		coordinates2[2][1] = y12;
		
		triangleMap.put(0,coordinates0);
		triangleMap.put(1,coordinates1);
		triangleMap.put(2,coordinates2);
		
		return triangleMap;
	}
	
	/**
	 * draws the different levels of the sierpinski triangle by recursively calling itself until a stopping condition is met.
	 * @param g
	 * @param x
	 * @param y
	 * @param levels
	 */
	private void drawRecursively(Graphics g, int[] x, int[] y, int levels) {
		if (levels == 0) return;
		//if (Math.abs(x[0] - x[1]) < 2) return;
		Random randomColorGenerator = new Random();
		int randomNumber = randomColorGenerator.nextInt(colors.length - 1);
		g.setColor(colors[randomNumber]);
		levels--;
		
		Map<Integer, int[][]> triangleMap = getNewTriangles(x, y);
		drawTriangles(g, x, y);
		
		for (int i = 0; i < triangleMap.size(); i++) {
			int[] x1 = new int[3];
			int[] y1 = new int[3];
			int[][] triangleArray =  triangleMap.get(i);
			for (int j = 0; j < triangleArray.length; j++) {
				for (int k = 0; k < triangleArray[j].length; k++) {
					if (k == 0) x1[j] = triangleArray[j][k];
					if (k == 1) y1[j] = triangleArray[j][k];
				}
			}
			drawRecursively(g, x1, y1,levels);
		}
	}
	
	/**
	 * draws a triangle provided by the x and y coordinates.
	 * @param g
	 * @param x
	 * @param y
	 */
	private void drawTriangles(Graphics g, int[] x, int[] y) {
		// we initially did the triangles by drawing three lines
//		g.drawLine(x[0], y[0], x[1], y[1]);
//		g.drawLine(x[0], y[0], x[2], y[2]);
//		g.drawLine(x[1], y[1], x[2], y[2]);
		Polygon p = new Polygon(x,y,3);
		g.fillPolygon(p);
	}
	
	/**
	 * paints a sierpinski triangle.
	 */
	public void paint(Graphics g) {
		int width = this.getWidth();
		int height = this.getHeight();
		int halfTriangleWidth = (int) (Math.tan((3.1415 / 180) * 30) * height);

		// check if window orientation is vertical or horizontal
		if(width >= height) {
			int[] x = {width / 2 - halfTriangleWidth, width / 2, (int) (width / 2.0 + halfTriangleWidth)};
			int[] y = {height, 0, height};
			drawRecursively(g, x,  y, levels);
		}
		else {
			int[] x = {0, width/2, width};
			int[] y = {height, (int) (Math.tan((3.1415 / 180) * 60) * (width / 2.0)), height};
			drawRecursively(g, x,  y, levels);
		}
	}
}
