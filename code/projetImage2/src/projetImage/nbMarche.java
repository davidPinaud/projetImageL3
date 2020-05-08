package projetImage;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Collections;
import java.util.Vector;

/**
 * Classe principale du programme, contient le main ou l'on peut rentrer le
 * chemin vers l'image a traiter et dérouler l'algorithme
 * 
 * @author davidpinaud
 *
 */
public class nbMarche {
	static int bestGuess; // meilleure approximation du nombre de marche
	static Vector<Integer> listeNbDeMarches = null; // tableau avec les approximations du nombre de marche

	public static void main(String[] args) {
		String chemin = "/Users/davidpinaud/Desktop/ImageL3-master/imagesProjet/escalier5.jpeg"; // chemin vers l'image
																									// a traiter

		BufferedImage image = Util.chargerImage(chemin);
		BufferedImage image2 = Util.chargerImage(chemin); // on charge deux fois l'image car le traitement est rapide et
															// le temps d'affichage d'une image fait que l'affichage de
															// l'image de départ est impossible sur la même variable
		Util.imshow(image2, "Image de départ");

		listeNbDeMarches = (Vector<Integer>) Util.nbDeLigne(image); // Appel de l'algorithme pour retourner un vecteur
																	// avec 3 approximations du nombre de marche
		System.out.println("Approximations trouvées : " + listeNbDeMarches);

		Collections.sort(listeNbDeMarches);
		int mediane = listeNbDeMarches.get(1);

		// la meilleure approximation est la moyenne des deux valeurs les plus proches,
		// par exemple si les valeurs sont 5,7,11 alors la meilleure approximation
		// serait 5+7/2=6 (nombre entier)
		if (Math.abs(listeNbDeMarches.get(0) - mediane) <= Math.abs(listeNbDeMarches.get(2) - mediane)) {
			bestGuess = (mediane + listeNbDeMarches.get(0)) / 2;
		} else {
			bestGuess = (mediane + listeNbDeMarches.get(2)) / 2;
		}

		System.out.println("nombre minimum de marches trouvé : " + listeNbDeMarches.get(0)
				+ "\nnombre maximum de marches trouvé : " + listeNbDeMarches.get(listeNbDeMarches.size() - 1)
				+ "\nmeilleure approximation : " + bestGuess);

	}



}
