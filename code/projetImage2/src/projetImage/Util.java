package projetImage;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * Classe utilitaire avec les fonctions nécessaire au bon fonctionnement du
 * programme. L'appel à nbLigne(BufferedImage image) va lancer le mécanisme du
 * calcul du nombre de marche. L'algorithme fonctionne mieux sur une image
 * d'escalier prise de face, centré, avec un bon niveau de luminosité et pas
 * autre chose dans le cadre
 * 
 * @author davidpinaud
 *
 */
public class Util {

	static int seuil; // seuil de l'image a traiter
	private static BufferedImage imageTemp; // image tempon qui est l'image a traiter binarisé, elle est utilisé pour le
											// filtrage médian 3x3 de l'image à traitze

	/**
	 * Fonction permettant de montrer une image avec Jframe.
	 * 
	 * @param image Image à montrer
	 * @param title Titre de l'image
	 */
	public static void imshow(BufferedImage image, String title) {
		JFrame frame = new JFrame();
		frame.getContentPane().add(new JLabel(new ImageIcon(image)));
		frame.setTitle(title);
		frame.pack();
		frame.setVisible(true);
	}

	/**
	 * Fonction permettant de charger une image en mémoire.
	 * 
	 * @param chemin Chemin de l'image à charger
	 * @return un BufferedImage contenant les données de l'image chargé.
	 */
	public static BufferedImage chargerImage(String chemin) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(chemin));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}

	/**
	 * Fonction qui pour une liste de Float, renvoi le maximum
	 * 
	 * @param list Une liste de Float
	 * @return Un float qui est le maximum de la liste
	 */
	public static Float maxListFloat(Vector<Float> list) {
		float max = list.get(0);
		for (int i = 1; i < list.size(); i++) {
			max = (max < list.get(i)) ? list.get(i) : max;
		}
		return max;
	}

	/**
	 * Fonction qui pour une liste de Float, renvoi le minimum
	 * 
	 * @param list Une liste de Float
	 * @return Un float qui est le minimum de la liste
	 */
	public static Float minListFloat(List<Float> list) {
		float min = list.get(0);
		for (int j = 1; j < list.size(); j++) {
			min = list.get(j) < min ? list.get(j) : min;
		}
		return min;
	}

	/**
	 * Fonction qui retourne la moyenne (entière) d'une liste d'entier
	 * 
	 * @param list Une liste d'entier
	 * @return Un int qui est la moyenne de la liste
	 */
	public static int moyenneTableauInt(List<Integer> list) {
		int sum = 0;
		for (int j = 0; j < list.size(); j++) {
			sum += list.get(j);
		}
		return (int) sum / list.size();
	}

	/**
	 * Fonction qui retourne l'index du plus petit élément d'une liste de Float
	 * 
	 * @param list Une liste de Float
	 * @return Un int étant l'index du plus petit élément de la liste donnée.
	 */
	public static int getIndexMinList(List<Float> list) {
		int min = 0;
		for (int j = 1; j < list.size(); j++) {
			min = list.get(j) < list.get(min) ? j : min;
		}
		return min;
	}

	/**
	 * Fonction qui, pour une image donné va tracer une ligne horizontale à la
	 * hauteur y jusqu'a la largeur x de la couleur color.
	 * 
	 * @param image L'image sur laquelle la ligne va être tracée.
	 * @param x     Largeur à laquelle la ligne va être tracée.
	 * @param y     Hauteur à laquelle la ligne va être tracée.
	 * @param color Couleur de la ligne tracée
	 */
	public static void tracerLigneHorizontale(BufferedImage image, int x, int y, Color color) {
		for (int i = 0; i <= x; i++) {
			image.setRGB(i, y, color.getRGB());
		}
	}

	/**
	 * Fonction qui, pour une image donné, va la transformer en negatif et la
	 * renvoyer.
	 * 
	 * @param image Le BufferedImage à rendre négatif.
	 * @return Le BufferedImage en négatif.
	 */
	public static BufferedImage toNegative(BufferedImage image) {
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

	/**
	 * Fonction qui retourne les composantes RGB d'une couleur sous la forme d'une
	 * tableau de composantes : [rouge,vert,bleu,alpha]
	 * 
	 * @param c Une couleur de type Color
	 * @return Un tableau d'entier qui sont les composantes de la couleur.
	 */
	public static int[] getComponentRGBdepuisCouleur(Color c) {
		int p = c.getRGB();
		int r = (p >> 16) & 0xff;
		int g = (p >> 8) & 0xff;
		int b = p & 0xff;
		int a = (p >> 24) & 0xff;
		int[] components = { r, g, b, a };
		return components;
	}

	/**
	 * Fonction qui retourne les composantes RGB d'un entier représentant le RGB
	 * d'un pixel sous la forme d'une tableau de composantes :
	 * [rouge,vert,bleu,alpha]
	 * 
	 * @param RGB int étant la composante RGB à décomposer
	 * @return Un tableau d'entier qui sont les composantes du RGB
	 */
	public static int[] getComponentRGBdepuisRGB(int RGB) {
		int p = RGB;
		int r = (p >> 16) & 0xff;
		int g = (p >> 8) & 0xff;
		int b = p & 0xff;
		int a = (p >> 24) & 0xff;
		int[] components = { r, g, b, a };
		return components;
	}

	public static float moyenneValeurPixel(List<pixel> listePixels) {
		float sum = 0;
		for (int j = 0; j < listePixels.size(); j++) {
			sum += listePixels.get(j).getRGB()[0];
		}
		return sum / listePixels.size();
	}

	/**
	 * Fonction qui permet de calculer la variance des intensités d'une liste de
	 * pixel
	 * 
	 * @param moyenne     La moyenne des intensités des pixels en Float
	 * @param listePixels la liste de pixels
	 * @return La variance des intensités de la liste de pixels donné.
	 */
	public static float varianceClasse(float moyenne, List<pixel> listePixels) {
		float sum = 0;
		for (int j = 0; j < listePixels.size(); j++) {
			sum += Math.pow((listePixels.get(j).getRGB()[0] - moyenne), 2);
		}
		return sum / listePixels.size();
	}

	/**
	 * Fonction implémentant l'algorithme d'Otsu qui permet de trouver le seuil
	 * optimal pour binariser une image. Ici on calcule le seuil tel que la variance
	 * intra classe est minimale. Il n'y a que deux classes car nous voulons
	 * seulement binariser l'image.
	 * 
	 * @param image Une image en niveau de gris
	 * @return un int étant le seuil optimal pour binariser l'image
	 */
	public static int ostu(BufferedImage image) {
		int w = image.getWidth();
		int h = image.getHeight();
		int[] componentsPixel;
		List<pixel> classeInferieure = null;
		List<pixel> classeSuperieure = null;
		Float varianceIntraClasse;
		Float wClasseInf = null, wClasseSup = null;
		List<Float> tableauVarianceIntraClasse = new Vector<>(); // List ou l'index est le seuil (0 à 255) et la valeur
																	// est la variance intra classe pour ce seuil;

		// POUR TOUS LES SEUILS NOUS ALLONS CALCULER LA VARIANCE INTRA CLASSE
		for (int k = 0; k < 255; k++) {
			classeInferieure = new Vector<>();
			classeSuperieure = new Vector<>();
			// REPARTITION DES PIXELS DANS LES CLASSES EN FONCTION DU SEUIL
			for (int x = 0; x < w; x++) {
				for (int y = 0; y < h; y++) {
					componentsPixel = getComponentRGBdepuisRGB(image.getRGB(x, y));
					if (componentsPixel[0] <= k) { // on teste ici si le pixel appartient à la classe inférieure (noire)
													// ou supérieure (blanc)
						classeInferieure.add(new pixel(x, y, componentsPixel));
					} else {
						classeSuperieure.add(new pixel(x, y, componentsPixel));
					}
				}
			}

			// CALCUL VARIANCE INTRA-CLASSE
			wClasseInf = (float) classeInferieure.size() / (float) (classeInferieure.size() + classeSuperieure.size());
			wClasseSup = (float) classeSuperieure.size() / (float) (classeInferieure.size() + classeSuperieure.size());
			varianceIntraClasse = wClasseInf * varianceClasse(moyenneValeurPixel(classeInferieure), classeInferieure)
					+ wClasseSup * varianceClasse(moyenneValeurPixel(classeSuperieure), classeSuperieure);

			// ON MET LA VARIANCE INTRA CLASSE DANS LE TABLEAU POUR LES COMPARER
			tableauVarianceIntraClasse.add(varianceIntraClasse);

		}

		// QUAND TOUTES LES VARIANCES INTRA CLASSES ONT ETE CALCULE ON PREND LA PLUS
		// PETITE

		return getIndexMinList(tableauVarianceIntraClasse); // on peut prendre l'index car la liste est ordonné de façon
															// à ce
															// que à l'index k, ce soit la valeur de la
															// varianceIntraClasse pour un seuil de niveau k
	}

	/**
	 * Cette fonction permet de renvoyer une liste d'entier qui sont les estimations
	 * du nombre de marches de l'image donné. Elle fait cela en comptants le nombres
	 * de changements de couleurs de l'histogramme de projection verticallement. On
	 * choisis les abscisses auxquels on compte a partir du plus grand sommet de
	 * l'histogramme de projection que l'on divise en 2,3 et 4.
	 * 
	 * @param image Une image d'escalier.
	 * @return Une liste contenant les estimations du nombre de marches la première
	 *         est pour un comptage avec comme valeur d'abscisse le max du sommet /
	 *         2 puis /3 et enfin /4.
	 */
	public static Vector<Integer> nbDeLigne(BufferedImage image) {
		BufferedImage HistogrameProjection = getHistoProjection(image);

		imshow(HistogrameProjection, " Histogramme de projection");

		// LE PLUS GRAND PIC DANS L'HISTOGRAMME DE PROJECTION
		int maxWidthHisto = 0;
		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				maxWidthHisto = (getComponentRGBdepuisRGB(image.getRGB(x, y))[0] == 255) & (x > maxWidthHisto) ? x
						: maxWidthHisto;
			}
		}

		int nbLigne;
		Boolean isBlack;
		Boolean isWhiteBoolean;
		int xOuCompter;
		Vector<Integer> nbLigneVector = new Vector<Integer>();

		// Calcul du nombre de marches selon une largeur donné en fonction du plus grand
		// pic de l'histogramme (/2,/3 et /4)
		for (int k = 2; k <= 4; k++) {
			nbLigne = 0;
			xOuCompter = maxWidthHisto / k;
			isBlack = getComponentRGBdepuisRGB(HistogrameProjection.getRGB(xOuCompter, 0))[0] == 0;
			nbLigne = 0;
			for (int y = 1; y < HistogrameProjection.getHeight(); y++) {
				isWhiteBoolean = (getComponentRGBdepuisRGB(HistogrameProjection.getRGB(xOuCompter, y))[0] == 255);
				if ((isBlack) && isWhiteBoolean) {
					nbLigne++;
					isBlack = false;
				} else if ((!isBlack)
						&& (getComponentRGBdepuisRGB(HistogrameProjection.getRGB(xOuCompter, y))[0] == 0)) {
					isBlack = true;
				}
			}
			nbLigneVector.add(nbLigne);
		}

		return (nbLigneVector);

	}

	/**
	 * Fonction qui pour une image donné va, après binarisation et filtrage (median
	 * 3x3) la transformer en un histogramme de projection.
	 * 
	 * @param image BufferedImage à transformer en histogramme de projection.
	 * @return Un BufferedImage qui est l'histogramme de projection de l'image donné
	 *         en paramètre
	 */
	public static BufferedImage getHistoProjection(BufferedImage image) {
		toBinaire(image);
		filtreMedianTroisTrois(image);

		Vector<Float> points = new Vector<Float>();
		for (int y = 0; y < image.getHeight(); y++) {// pour chaque hauteur, on ajoute un point par pixel présent
			points.add(y, (float) 0.0);
			for (int x = 0; x < image.getWidth(); x++) {
				if (getComponentRGBdepuisRGB(image.getRGB(x, y))[0] == 0) {
					points.set(y, points.get(y) + 1);
				}
			}
		}

		BufferedImage graph = new BufferedImage((int) (maxListFloat(points) + 1), image.getHeight(),
				BufferedImage.TYPE_INT_RGB); // on crée le graphe en fonction de la taille du vecteur
		for (int y = 0; y < points.size(); y++) {
			tracerLigneHorizontale(graph, Math.round(points.get(y)), y, Color.white);// chaque ligne correspond à une
																						// hauteur
		}

		imshow(image, " image en niveau de gris normalisé+ binarisé+filtré médian");// 4eme

		return graph;

	}

	/**
	 * Fonction qui pour une image donné va, après passage de l'image en nuances de
	 * gris, la binariser selon un seuil calculé ave l'algorithme d'ostu.
	 * 
	 * @param image BufferedImage a binariser
	 * @return Un BufferedImage binarisé.
	 */
	public static BufferedImage toBinaire(BufferedImage image) {
		System.out.println("Calcul de l'image en niveau de gris ...");
		toGreyScale(image);
		System.out.println("Calcul de l'image normalisé ...");
		image = normalise(image);

		System.out.println("Calcul Seuil ...");
		seuil = ostu(image);

		System.out.println("seuil : " + seuil);

		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				if (getComponentRGBdepuisRGB(image.getRGB(x, y))[0] < seuil) {
					image.setRGB(x, y, new Color(0, 0, 0).getRGB());
				} else {
					image.setRGB(x, y, new Color(255, 255, 255).getRGB());
				}
			}
		}
		imshow(image, "image en niveau de gris + normalisé + binarisé");

		// On fait une copie de l'image binarisé que l'on met en static
		imageTemp = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				imageTemp.setRGB(x, y, image.getRGB(x, y));
			}
		}

		return image;
	}

	/**
	 * Fonction qui permet pour une image donné de la transformer une image en
	 * nuances de gris
	 * 
	 * @param image BufferedImage a transformer en image en nuances de gris
	 * @return Un BufferedImage en nuances de gris
	 */
	public static BufferedImage toGreyScale(BufferedImage image) {
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
		imshow(image, "image en niveau de gris");
		return image;
	}

	/**
	 * Fonction qui normalise une image donnée
	 * 
	 * @param image BufferedImage à normaliser
	 * @return Un BufferedImage normalisé
	 */
	public static BufferedImage normalise(BufferedImage image) {
		int w = image.getWidth();
		int h = image.getHeight();
		int min = 255, max = 0;

		for (int l = 0; l < h; l++) {// calcule min max
			for (int c = 0; c < w; c++) {
				min = Util.getComponentRGBdepuisRGB(image.getRGB(c, l))[0] > min ? min
						: Util.getComponentRGBdepuisRGB(image.getRGB(c, l))[0];
				max = Util.getComponentRGBdepuisRGB(image.getRGB(c, l))[0] < max ? max
						: Util.getComponentRGBdepuisRGB(image.getRGB(c, l))[0];
			}
		}

		BufferedImage myImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

		Double grey;

		for (int l = 0; l < h; l++) {
			for (int c = 0; c < w; c++) {
				grey = 255 * (((double) Util.getComponentRGBdepuisRGB(image.getRGB(c, l))[0]) - min)
						/ ((double) max - min);
				myImage.setRGB(c, l,
						new Color((int) Math.round(grey), (int) Math.round(grey), (int) Math.round(grey)).getRGB());
			}
		}

		imshow(myImage, "image niveau de gris + normalisé");

		return myImage;
	}

	/**
	 * Fonction qui va appliquer un filtre médian 3x3 sur une image BINAIRE donnée.
	 * Cette fonction requiert l'appel de la binarisation pour fonctionner. En
	 * effet, elle a besoin d'une copie de l'image binarisé dans le programme pour
	 * éviter de prendre des valeurs érronés.
	 * 
	 * @param imageBinaire BufferedImage sur laquelle on va appliquer le filtre médian
	 * @return Un BufferedImage ou on a appliqué un filtre médian
	 */
	public static BufferedImage filtreMedianTroisTrois(BufferedImage imageBinaire) {
		System.out.println("Calcul image filtré au filtre médian 3x3 ...");
		int w = imageBinaire.getWidth();
		int h = imageBinaire.getHeight();
		int valeurMediane;

		for (int l = 1; l < h - 1; l++) {
			for (int c = 1; c < w - 1; c++) {
				valeurMediane = trouverMediane(imageTemp, l, c); // utilisation de l'image mise en static
				imageBinaire.setRGB(c, l, new Color(valeurMediane, valeurMediane, valeurMediane).getRGB());
			}
		}

		return imageBinaire;
	}

	/**
	 * Fonction qui va pour une image BINAIRE et des coordonnées données, retourner
	 * la médiane (255 ou 0 dans le cas d'une image binaire)
	 * 
	 * @param image BufferedImage ou prendre les valeurs
	 * @param l     int qui est la coordonnée sur les lignes
	 * @param c     int qui est la coordonnée sur les colonnes
	 * @return un int de valeur 255 ou 0 (l'image est binaire)
	 */
	public static int trouverMediane(BufferedImage image, int l, int c) {

		int somme = Util.getComponentRGBdepuisRGB(image.getRGB(c, l))[0]
				+ Util.getComponentRGBdepuisRGB(image.getRGB(c + 1, l))[0]
				+ Util.getComponentRGBdepuisRGB(image.getRGB(c, l + 1))[0]
				+ Util.getComponentRGBdepuisRGB(image.getRGB(c + 1, l + 1))[0]
				+ Util.getComponentRGBdepuisRGB(image.getRGB(c - 1, l - 1))[0]
				+ Util.getComponentRGBdepuisRGB(image.getRGB(c - 1, l))[0]
				+ Util.getComponentRGBdepuisRGB(image.getRGB(c, l - 1))[0]
				+ Util.getComponentRGBdepuisRGB(image.getRGB(c + 1, l - 1))[0]
				+ Util.getComponentRGBdepuisRGB(image.getRGB(c - 1, l + 1))[0];

		return somme >= (255 * 5) ? 255 : 0;
	}

}
