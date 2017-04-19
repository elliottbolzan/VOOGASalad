package player;

import java.util.ResourceBundle;

import authoring.components.ComponentMaker;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import polyglot.Case;
import polyglot.Polyglot;

/**
 * Abstract class of menu that defines all the common features of each menu
 * 
 * @author Jesse
 *
 */
public abstract class AbstractMenu extends BorderPane implements Menu {

	private Polyglot polyglot;
	private ResourceBundle IOResources;
	private ComponentMaker factory;
	private Button back;
	private Stage stage;
	private String fontPath;
	private Loader loader;

	public AbstractMenu(Stage stage, Loader loader, String title, Polyglot polyglot, ResourceBundle IOResources) {
		this.polyglot = polyglot;
		this.IOResources = IOResources;
		this.loader = loader;
		this.fontPath = IOResources.getString("FontPath");
		loadFont();
		setupView(stage, title);
	}

	private void setupView(Stage stage, String title) {
		this.stage = stage;
		stage.titleProperty().bind(polyglot.get("PlayerTitle", Case.TITLE));
		stage.getIcons().add(new Image(this.getResources().getString("IconPath")));
		factory = new ComponentMaker(polyglot, this.getResources().getString("StylesheetPath"));
		setTitle(title);
		setupBackButton();
	}

	private void setupBackButton() {
		ImageView image = new ImageView(
				new Image(getClass().getClassLoader().getResourceAsStream(getResources().getString("BackPath"))));
		back = factory.makeImageButton("Back", image, e -> back(stage), false);
		this.setBottom(backButton());
	}

	protected Loader getLoader() {
		return loader;
	}
	
	protected void setTitleFixed(String title) {
		Label label = new Label(title);
		setTitleHelper(label);
	}

	protected void setTitle(String titleProperty) {
		Label label = new Label();
		label.textProperty().bind(polyglot.get(titleProperty, Case.TITLE));
		setTitleHelper(label);
	}
	
	private void setTitleHelper(Label label) {
		label.setId("title");
		label.setFont(Font.font(IOResources.getString("FontPath"), FontWeight.BOLD, 30));
		label.setTextFill(Color.YELLOW);
		this.setTop(label);
		BorderPane.setAlignment(label, Pos.CENTER);
	}

	protected ResourceBundle getResources() {
		return IOResources;
	}

	protected ComponentMaker getFactory() {
		return factory;
	}

	protected Button backButton() {
		return back;
	}

	protected void loadFont() {
		Font.loadFont(fontPath, 10);
	}

	public void back(Stage stage) {
		new MainMenu(stage, loader, polyglot, IOResources);
	}

	protected Stage getStage() {
		return stage;
	}

	public Scene display() {
		Scene scene = new Scene(this, 1000, 600);
		scene.getStylesheets().add(getResources().getString("StylesheetPath"));
		return scene;
	}

}
