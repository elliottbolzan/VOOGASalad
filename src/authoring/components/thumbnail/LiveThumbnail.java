package authoring.components.thumbnail;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.Image;

/**
 * @author Elliott Bolzan
 *
 */
public class LiveThumbnail extends Thumbnail {

	private SimpleStringProperty path;
	
	/**
	 * @param width
	 * @param height
	 */
	public LiveThumbnail(SimpleStringProperty path, int width, int height) {
		super(width, height);
		this.path = path;
		path.addListener(e -> setImage());
		setImage();
	}
	
	private void setImage() {
		if (path.get().startsWith("file:"))
			getImage().set(new Image(path.get()));
	}

}
