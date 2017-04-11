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
import javafx.scene.text.Font;
import javafx.stage.Stage;
import starter.StartMenu;

public abstract class AbstractMenu implements Menu{
	private ResourceBundle resources = ResourceBundle.getBundle("resources/Player");
	private BorderPane root;	
	private ComponentMaker factory;
	private Button back;
	private String gameFolderPath;
	private Stage stage;
	private String fontPath = resources.getString("FontPath");

	public AbstractMenu(Stage stage, String gameFolderPath, String title){
		loadFont();
		setupView(stage, gameFolderPath, title);
	}

	private void setupView(Stage stage, String gameFolderPath, String title){
		root = new BorderPane();
		this.stage = stage;
		this.gameFolderPath = gameFolderPath;
		factory = new ComponentMaker(resources);
		ImageView image = new ImageView(
				new Image(getClass().getClassLoader().getResourceAsStream(getResources().getString("BackPath"))));
		
		back = factory.makeImageButton("Back", image, e -> back(stage), false);
		getRoot().setBottom(backButton());
		Label titleLabel = new Label(resources.getString(title));
		titleLabel.setId("title");
		getRoot().setTop(titleLabel);
		BorderPane.setAlignment(titleLabel, Pos.CENTER);
	}
	
	protected BorderPane getRoot(){
		return root;
	}
	
	protected ResourceBundle getResources(){
		return resources;
	}
	
	protected ComponentMaker getFactory(){
		return factory;
	}
	
	protected Button backButton(){
		return back;
	}

	protected void loadFont() {
		Font.loadFont(fontPath, 10);
	}
	
	public void back(Stage stage){
		new PlayerMenu(stage, gameFolderPath);
	}
	
	public Scene display(){
		Scene scene = new Scene(getRoot(), 1000, 600);
		scene.getStylesheets().add(getResources().getString("StylesheetPath"));
		return scene;	
	}
	
}
