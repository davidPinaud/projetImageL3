package projetImage;

import java.awt.image.BufferedImage;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class AfficheImage extends BorderPane{
	BufferedImage buffImage;
	Image image;
	
	public AfficheImage(BufferedImage buffImage) {
		this.buffImage=buffImage;
		this.image=SwingFXUtils.toFXImage(buffImage, null);
		ImageView imageView=new ImageView(image);
		this.setCenter(imageView);
	}
}
