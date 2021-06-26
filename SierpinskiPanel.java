import java.awt.Color;
import java.awt.Graphics;
import java.awt.Panel;
import java.awt.Polygon;
import java.util.HashMap;
import java.util.Map;

public class SierpinskiPanel extends Panel {
	private int levels= 4;
	//this.getWidth();
	private Graphics g;
	private Color[] colors = {new Color(0x172a3a), new Color(0x004346), new Color(0x383293), new Color(0xabcdef)};
	
	private Map<Integer, int[][]> getNewTriangles(int[] x,int[] y){
		Map<Integer, int[][]> triangleMap = new HashMap<>();
		int x01= (x[0]+x[1])/2;
		int x12= (x[1]+x[2])/2;
		int x02= (x[0]+x[2])/2;
		
		int y01= (y[0]+y[1])/2;
		int y12= (y[1]+y[2])/2;
		int y02= (y[0]+y[2])/2;
		
		int[][] coordinates0 = new int[3][3];
		coordinates0[0][0]= x[0];
		coordinates0[0][1]= y[0];
		coordinates0[1][0]= x01;
		coordinates0[1][1]= y01;
		coordinates0[2][0]= x02;
		coordinates0[2][1]= y02;

		int[][] coordinates1 = new int[3][3];
		coordinates1[0][0]= x[1];
		coordinates1[0][1]= y[1];
		coordinates1[1][0]= x01;
		coordinates1[1][1]= y01;
		coordinates1[2][0]= x12;
		coordinates1[2][1]= y12;
		
		int[][] coordinates2 = new int[3][3];
		coordinates2[0][0]= x[2];
		coordinates2[0][1]= y[2];
		coordinates2[1][0]= x02;
		coordinates2[1][1]= y02;
		coordinates2[2][0]= x12;
		coordinates2[2][1]= y12;
		
		
		triangleMap.put(0,coordinates0);
		triangleMap.put(1,coordinates1);
		triangleMap.put(2,coordinates2);
		
		
		return triangleMap;
	}
	
	//calls getNewTriangles, extracts the corners of the new triangles 
	//and calls drawTriangles for all new triangles
	private void drawRecursively(Graphics g, int[] x, int[] y, int levels) {
			if (levels ==0) return;
			
			g.setColor(colors[levels-1]);
			levels--;
			drawTriangles(g, x, y);
			
			Map<Integer, int[][]> triangleMap = getNewTriangles(x,  y);

			for (int i=0; i<triangleMap.size(); i++) {
				int[] x1 = new int[3];
				int[] y1 = new int[3];
				int[][] triangleArray =  triangleMap.get(i);
				for (int j=0; j<triangleArray.length; j++) {
					for (int k=0; k<triangleArray[j].length; k++) {
						if (k==0) x1[j]=triangleArray[j][k];
						if (k==1) y1[j]=triangleArray[j][k];
					}
				}
				drawRecursively(g, x1, y1,levels);
			}
			

	}
	
	private void drawTriangles(Graphics g, int[] x, int[] y) {
//		g.drawLine(x[0], y[0], x[1], y[1]);
//		g.drawLine(x[0], y[0], x[2], y[2]);
//		g.drawLine(x[1], y[1], x[2], y[2]);
		Polygon p = new Polygon(x,y,3);
		g.fillPolygon(p);
		
	}
	
	public void paint(Graphics g) {
		int width= this.getWidth();
		int height= this.getHeight();
		int halfTriangleWidth= (int) (Math.tan((3.1415/180)*30)* height);


		if(width>height) {
			//adjust so  that the width does NOT have to be langer thatn the height
			int[] x = {width/2 -halfTriangleWidth, width/2, (int) (width/2.0 +halfTriangleWidth)};
			int[] y = {height, 0, height};
			drawRecursively(g, x,  y, levels);
		}
		else {
			int[] x = {0, width/2, width};
			int[] y = {height, (int) (Math.tan((3.1415/180)*30)* (width/2.0)), height};
			drawRecursively(g, x,  y, levels);
		}
		
		
	}
	
	private void draw(Graphics g, int[] x, int[] y) {
		System.out.println("hi");
		drawRecursively(g,  x,  y, levels);
		
	}
}
