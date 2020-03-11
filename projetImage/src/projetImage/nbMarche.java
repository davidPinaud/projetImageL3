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
		BufferedImage image5=Util.chargerImage("/Users/david_pinaud/Desktop/ImageL3-master/imagesProjet/escalier1.jpg");
		
		
		BufferedImage image6=Util.chargerImage("/Users/david_pinaud/Desktop/ImageL3-master/imagesProjet/escalier1.jpg");
		Util.imshow(image6);


		System.out.println(Util.nbDeLigne(image5, 120));
		
		//stage.setScene(new Scene(new AfficheImage(image5)));
//		stage.sizeToScene();
//		stage.show();
	}

}
