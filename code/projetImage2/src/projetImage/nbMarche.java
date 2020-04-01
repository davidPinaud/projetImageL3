package projetImage;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

import javafx.scene.Scene;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;

public class nbMarche extends Application{
	static final int SEUIL = 125;

	public static void main(String[] args)  {
		launch(args);
		//ColorAdjust colorAdjust = new ColorAdjust();
		//colorAdjust.setContrast(0.1);
		//colorAdjust.setHue(-0.05);
		//colorAdjust.setBrightness(0.1);
		//colorAdjust.setSaturation(0.2);

		//Image image = new Image(new FileInputStream("/Users/david_pinaud/Desktop/ImageL3-master/imagesProjet/escalier1.jpg"));
		//ImageView imageView = new ImageView();
		//imageView.setEffect(colorAdjust);
		
		//BufferedImage image5=SwingFXUtils.fromFXImage(image,null);
		
		

	}

	@Override
	public void start(Stage stage) throws Exception {
		String chemin="/Users/david_pinaud/Desktop/ImageL3-master/imagesProjet/escalier4.jpeg";
		BufferedImage image5=Util.chargerImage(chemin);
		
		
		BufferedImage image6=Util.chargerImage(chemin);
		Util.imshow(image6);


		System.out.println(Util.nbDeLigne(image5, 125,chemin));
		
		//stage.setScene(new Scene(new AfficheImage(image5)));
//		stage.sizeToScene();
//		stage.show();
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
