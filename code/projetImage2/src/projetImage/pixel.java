package projetImage;

/**
 * Classe permettant de représenter un pixel
 * @author davidpinaud
 *
 */
public class pixel {
	
	int x,y;
	int[] RGB;
	/**
	 * Constructeur de la classe
	 * @param x abscisse du pixel
	 * @param y ordonnée du pixel
	 * @param rGB tableau d'entiers représentant les niveaux RGB du pixel du type [rouge,vert,bleu,alpha]
	 */
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
