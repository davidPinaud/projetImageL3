package projetImage;

public class pixel {
	int x,y;
	int[] RGB;
	public pixel(int x, int y, int[] rGB) {
		super();
		this.x = x;
		this.y = y;
		RGB = rGB;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int[] getRGB() {
		return RGB;
	}
	public void setRGB(int[] rGB) {
		RGB = rGB;
	}
	
}
