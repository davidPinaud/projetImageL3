package projetImage;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Collections;
import java.util.Vector;



public class nbMarche{
	static int bestGuess;
	static Vector<Integer> listeNbDeMarches = null ;


	public static void main(String[] args)  {
		String chemin="/Users/davidpinaud/Desktop/ImageL3-master/imagesProjet/escalier5.jpeg";
		BufferedImage image=Util.chargerImage(chemin);
		
		
		BufferedImage image2=Util.chargerImage(chemin);
		Util.imshow(image2,"Image de départ");
		

		listeNbDeMarches=(Vector<Integer>) Util.nbDeLigne(image);
		System.out.println(listeNbDeMarches);

		Collections.sort(listeNbDeMarches);
		int mediane=listeNbDeMarches.get(1);
		if(Math.abs(listeNbDeMarches.get(0)-mediane)<=Math.abs(listeNbDeMarches.get(2)-mediane)) {
			bestGuess=(mediane+listeNbDeMarches.get(0))/2;
		}else {
			bestGuess=(mediane+listeNbDeMarches.get(2))/2;
		}
		
		System.out.println("nombre minimum de marches trouvé : "+listeNbDeMarches.get(0)+
				"\nnombre maximum de marches trouvé : "+listeNbDeMarches.get(listeNbDeMarches.size()-1)+
				"\nmeilleure approximation : "+bestGuess);
		
		

	}

	
	//Calculer le nb de ligne
		//Contraster l'image pour augmenter les différences des RGB des pixels (pour avoir plus de marge sur le seuil) pas fait
		//Ostu pour calculer le seuil pas fait
		//Calculer l'histogramme
			//Binariser
				//Normaliser
					//Mettre en niveaux de gris
			//filtre median pour enlever le bruit
		//
	
	
	//Mettre en niveaux de gris
	//normalise
	//ostu pour le seuil
	//binariser
	//filtre median
	// faire lhistogramme de projection
	//calculer le nb de ligne

}
