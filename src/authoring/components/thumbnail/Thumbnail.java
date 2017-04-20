package authoring.components.thumbnail;

import utils.views.View;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * @author Elliott Bolzan
 *
 */
public abstract class Thumbnail extends View {

	private SimpleObjectProperty<Image> image;
	private ImageView imageView = new ImageView();

	public Thumbnail(int width, int height) {
		image = new SimpleObjectProperty<Image>();
		imageView.setFitWidth(width);
		imageView.setFitHeight(height);
		imageView.setPreserveRatio(true);
		imageView.imageProperty().bind(image);
		setCenter(imageView);
		setMinSize(0, 0);
	}
	
	public SimpleObjectProperty<Image> getImage() {
		return image;
	}

}
