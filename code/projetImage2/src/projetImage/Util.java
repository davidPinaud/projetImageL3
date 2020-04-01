package projetImage;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Util{
	public static void imshow(BufferedImage image) throws IOException {
		// Instantiate JFrame
		JFrame frame = new JFrame();

		// Set Content to the JFrame
		frame.getContentPane().add(new JLabel(new ImageIcon(image)));
		frame.pack();
		frame.setVisible(true);
	}

	public static BufferedImage chargerImage(String chemin) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(chemin));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return img;

	}



	public static Float maxList(Vector<Float> list) {
		float max = list.get(0);
		for (int i = 1; i < list.size(); i++) {
			max = (max < list.get(i)) ? list.get(i) : max;
		}
		return max;
	}

	public static void tracerLigneHorizontale(BufferedImage image, int x, int y, Color color) {
		for (int i = 0; i <= x; i++) {
			image.setRGB(i, y, color.getRGB());
		}
	}

	

	

	public static BufferedImage toNegative(BufferedImage image,String chemin) {
		int[] RGBPixel;
		for (int x = 0; x < image.getHeight(); x++) {
			for (int y = 0; y < image.getWidth(); y++) {
				RGBPixel = getComponentRGBdepuisRGB(image.getRGB(y, x));
				RGBPixel[0] = 255 - RGBPixel[0];
				RGBPixel[1] = 255 - RGBPixel[1];
				RGBPixel[2] = 255 - RGBPixel[2];
				Color color = new Color(RGBPixel[0], RGBPixel[1], RGBPixel[2]);
				image.setRGB(y, x, color.getRGB());

			}
		}
		return image;
	}

	public static int[] getComponentRGBdepuisCouleur(Color c) {
		int p = c.getRGB();
		int r = (p >> 16) & 0xff;
		int g = (p >> 8) & 0xff;
		int b = p & 0xff;
		int a = (p >> 24) & 0xff;
		int[] components = { r, g, b, a };
		return components;
	}

	public static int[] getComponentRGBdepuisRGB(int RGB) {
		int p = RGB;
		int r = (p >> 16) & 0xff;
		int g = (p >> 8) & 0xff;
		int b = p & 0xff;
		int a = (p >> 24) & 0xff;
		int[] components = { r, g, b, a };
		return components;
	}

	public static int nbDeLigne(BufferedImage image, int seuil,String chemin) throws IOException {
		BufferedImage graph = getHistoProjection(image, seuil,chemin);

		System.out.println(5+" graphe");
		try {
			imshow(graph);//5eme
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		int nbLigne = 0;
		Boolean isBlack = getComponentRGBdepuisRGB(graph.getRGB(image.getWidth() / 2, 0))[0] == 0;

		for (int y = 1; y < graph.getHeight(); y++) {
			if ((isBlack) && (getComponentRGBdepuisRGB(graph.getRGB(image.getWidth() / 2, y))[0] == 255)) { // l'alternance
																											// entre un
																											// pixel
																											// noir et
																											// un pixel
																											// blanc
																											// ajoute
																											// une ligne
				nbLigne++;
				isBlack = false;
			}
			if ((!isBlack) && (getComponentRGBdepuisRGB(graph.getRGB(image.getWidth() / 2, y))[0] == 0)) {
				isBlack = true;
			}
		}

		return nbLigne;

	}
	
	public static BufferedImage getHistoProjection(BufferedImage image, int seuil,String chemin) throws IOException {
		toBinaire(image, seuil,chemin,true);
		Util.filtreMedianTroisTrois(image, chemin, seuil);

		Vector<Float> points = new Vector<Float>();
		for (int y = 0; y < image.getHeight(); y++) {// pour chaque hauteur, on ajoute un point par pixel présent
			points.add(y, (float) 0.0);
			for (int x = 0; x < image.getWidth(); x++) {
				if (getComponentRGBdepuisRGB(image.getRGB(x, y))[0] == 0) {
					points.set(y, points.get(y) + 1);
				}
			}
		}

		BufferedImage graph = new BufferedImage((int) (maxList(points) + 1), image.getHeight(),
				BufferedImage.TYPE_INT_RGB); // on crée le graphe en fonction de la taille du vecteur
		int m;
		for (int y = 0; y < points.size(); y++) {
			tracerLigneHorizontale(graph, Math.round(points.get(y)), y, Color.white);// chaque ligne correspond à une
																						// hauteur
		}

		System.out.println(4 +" image normalisé+ binarisé+filtré médian");

		try {
			imshow(image);//4eme
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		return graph;

	}
	
	public static BufferedImage toBinaire(BufferedImage image, int seuil,String chemin, boolean b) throws IOException {
		toGreyScale(image,chemin,b);

		image=Util.normalise(image,b);

		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				if (getComponentRGBdepuisRGB(image.getRGB(x, y))[0] < seuil) {
					image.setRGB(x, y, new Color(0, 0, 0).getRGB());
				} else {
					image.setRGB(x, y, new Color(255, 255, 255).getRGB());
				}
			}
		}
		
		if(b) {
		System.out.println(3+" image normalisé +binarisé");

		try {
			imshow(image);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//3eme
		}

		return 	image;
	}
	
	public static BufferedImage toGreyScale(BufferedImage image,String chemin,boolean b)  {
		float moyenneRGB;
		int[] RGBPixel;
		for (int x = 0; x < image.getHeight(); x++) {
			for (int y = 0; y < image.getWidth(); y++) {
				RGBPixel = getComponentRGBdepuisRGB(image.getRGB(y, x));
				moyenneRGB = (((float) RGBPixel[0]) + ((float) RGBPixel[1]) + ((float) RGBPixel[2])) / 3;
				Color color = new Color((int) moyenneRGB, (int) moyenneRGB, (int) moyenneRGB);
				image.setRGB(y, x, color.getRGB());

			}
		}
		if(b) {
		System.out.println(1+" image niveaux de gris");

		try {
			imshow(image);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//1er
		}

		return image;
	}
	
	public static BufferedImage normalise(BufferedImage image,boolean b) {
		int w=image.getWidth();
		int h=image.getHeight();
		int min=255,max=0;

		for(int l=0;l<h;l++) {//calcule min max
			for(int c=0;c<w;c++) {
				min=Util.getComponentRGBdepuisRGB(image.getRGB(c, l))[0]>min?min:Util.getComponentRGBdepuisRGB(image.getRGB(c, l))[0];
				max=Util.getComponentRGBdepuisRGB(image.getRGB(c, l))[0]<max?max:Util.getComponentRGBdepuisRGB(image.getRGB(c, l))[0];
			}
		}

		BufferedImage myImage=new BufferedImage(w,h,BufferedImage.TYPE_INT_RGB);

		Double grey;

		for(int l=0;l<h;l++) {
			for(int c=0;c<w;c++) {
				grey=255*(((double) Util.getComponentRGBdepuisRGB(image.getRGB(c, l))[0])-min)/((double)max-min);
				myImage.setRGB(c, l, new Color((int) Math.round(grey),(int) Math.round(grey),(int) Math.round(grey)).getRGB());
			}
		}
		if(b) {
		System.out.println(2+" image normalisé");

		try {
			imshow(myImage);//deuxieme
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}


		return myImage;
	}




	public static BufferedImage filtreMedianTroisTrois(BufferedImage image,String chemin,int seuil) throws IOException {
		int w=image.getWidth();
		int h=image.getHeight();
		int valeurMediane;

		BufferedImage image2=Util.chargerImage(chemin);
		Util.toBinaire(image2, seuil, chemin,false);

		for(int l=1;l<h-1;l++) {
			for(int c=1;c<w-1;c++) {
				valeurMediane=trouverMediane(image2,l,c);
				image.setRGB(c, l, new Color(valeurMediane,valeurMediane,valeurMediane).getRGB());
			}
		}


		return image;
	}

	public static int trouverMediane(BufferedImage image,int l,int c) {

		int somme=Util.getComponentRGBdepuisRGB(image.getRGB(c, l))[0]+Util.getComponentRGBdepuisRGB(image.getRGB(c+1, l))[0]+Util.getComponentRGBdepuisRGB(image.getRGB(c, l+1))[0]+Util.getComponentRGBdepuisRGB(image.getRGB(c+1, l+1))[0]+Util.getComponentRGBdepuisRGB(image.getRGB(c-1, l-1))[0]+Util.getComponentRGBdepuisRGB(image.getRGB(c-1, l))[0]+Util.getComponentRGBdepuisRGB(image.getRGB(c, l-1))[0]+Util.getComponentRGBdepuisRGB(image.getRGB(c+1, l-1))[0]+Util.getComponentRGBdepuisRGB(image.getRGB(c-1, l+1))[0];

		return somme>=(255*5)?255:0;
	}





}
