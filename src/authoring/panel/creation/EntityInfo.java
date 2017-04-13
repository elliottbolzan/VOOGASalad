/**
 * 
 */
package authoring.panel.creation;

import java.io.File;

import authoring.Workspace;
import authoring.components.ComponentMaker;
import authoring.components.thumbnail.FixedThumbnail;
import authoring.views.View;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

/**
 * @author Elliott Bolzan
 *
 */
public class EntityInfo extends View {

	private Workspace workspace;
	private EntityMaker editor;
	private ComponentMaker componentMaker;
	private TextField nameField;
	private FixedThumbnail thumbnail;
	private Slider scaleSlider;

	/**
	 * @param title
	 */
	public EntityInfo(Workspace workspace, EntityMaker editor) {
		super("Info");
		this.workspace = workspace;
		this.editor = editor;
		setup();
	}

	private void setup() {
		componentMaker = new ComponentMaker(workspace.getResources());
		VBox box = new VBox(20);
		box.getChildren().addAll(createNameBox(), new Separator(), createImageBox(), new Separator(), createScaleBox(),
				new Separator(), createButtonBar());
		box.setPadding(new Insets(20));
		setCenter(box);
	}

	private HBox createNameBox() {
		HBox nameBox = new HBox(8);
		Label nameLabel = new Label(workspace.getResources().getString("TitlePrompt"));
		nameField = new TextField();
		nameField.setPrefWidth(100);
		nameField.setText(editor.getEntity().nameProperty().get());
		nameBox.getChildren().addAll(nameLabel, nameField);
		nameBox.setAlignment(Pos.CENTER);
		return nameBox;
	}

	private VBox createImageBox() {
		VBox imageBox = new VBox(20);
		thumbnail = new FixedThumbnail(editor.getEntity().getImagePath(), 50, 50);
		Button pickButton = componentMaker.makeButton("PickButton", e -> pickImage(), true);
		imageBox.getChildren().addAll(thumbnail, pickButton);
		imageBox.setAlignment(Pos.CENTER);
		return imageBox;
	}

	private VBox createScaleBox() {
		VBox scaleBox = new VBox(8);
		Label scaleLabel = new Label(workspace.getResources().getString("ScaleSliderTitle"));
		double width = editor.getEntity().widthProperty().get();
		scaleSlider = new Slider(0, 1, width == 0 ? 1 : width / getImage().getWidth());
		scaleSlider.setMajorTickUnit(0.25);
		scaleSlider.setShowTickMarks(true);
		scaleSlider.setShowTickLabels(true);
		scaleBox.getChildren().addAll(scaleSlider, scaleLabel);
		scaleBox.setAlignment(Pos.CENTER);
		return scaleBox;
	}

	private HBox createButtonBar() {
		HBox buttonBar = new HBox();
		Button saveButton = componentMaker.makeButton("SaveButtonEditor", e -> editor.save(), true);
		Button cancelButton = componentMaker.makeButton("CancelButton", e -> editor.dismiss(), true);
		buttonBar.getChildren().addAll(saveButton, cancelButton);
		return buttonBar;
	}

	private void pickImage() {
		FileChooser imageChooser = componentMaker.makeFileChooser(
				System.getProperty("user.dir") + workspace.getResources().getString("DefaultDirectory"), "Images",
				"*.png", "*.jpg", "*.gif");
		File file = imageChooser.showOpenDialog(getScene().getWindow());
		if (file != null) {
			thumbnail.setImage(file.toURI().toString());
		}
	}

	public String getName() {
		return nameField.getText();
	}

	public String getImagePath() {
		return thumbnail.getImagePath();
	}

	private Image getImage() {
		return new Image(getImagePath());
	}

	public double getImageWidth() {
		return getImage().getWidth() * scaleSlider.getValue();
	}

	public double getImageHeight() {
		return getImage().getHeight() * scaleSlider.getValue();
	}

}
