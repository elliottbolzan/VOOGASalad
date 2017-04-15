package player;

import java.util.ResourceBundle;

import authoring.components.ComponentMaker;
import game_data.Game;
import game_data.GameData;
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

/**
 * Abstract class of menu that defines all the common features of each menu
 * @author Jesse
 *
 */
public abstract class AbstractMenu extends BorderPane implements Menu{
	private ResourceBundle resources = ResourceBundle.getBundle("resources/Player");
	private ComponentMaker factory;
	private Button back;
	private Stage stage;
	private String gameFolderPath;
	private String fontPath = resources.getString("FontPath");
	private Game game;
	private GameData data;

	public AbstractMenu(Stage stage, String gameFolderPath, String title){
		loadFont();
		setupView(stage, gameFolderPath, title);
		setupData();
	}

	private void setupView(Stage stage, String gameFolderPath, String title){
		this.stage = stage;
		stage.setTitle(this.getResources().getString("PlayerTitle"));
		stage.getIcons().add(new Image(this.getResources().getString("IconPath")));
		this.gameFolderPath = gameFolderPath;
		factory = new ComponentMaker(resources);

		setTitle(resources.getString(title));
		setupBackButton();
	}
	
	private void setupData(){
		data = new GameData();
		game = data.loadGame(gameFolderPath);
	}
	
	private void setupBackButton(){
		ImageView image = new ImageView(
				new Image(getClass().getClassLoader().getResourceAsStream(getResources().getString("BackPath"))));
		back = factory.makeImageButton("Back", image, e -> back(stage), false);
		this.setBottom(backButton());
	}
	
	protected void setTitle(String title){
		Label titleLabel = new Label(title);
		titleLabel.setId("title");
		titleLabel.setFont(Font.font(resources.getString("FontPath"), FontWeight.BOLD, 30));
		titleLabel.setTextFill(Color.YELLOW);
		this.setTop(titleLabel);
		BorderPane.setAlignment(titleLabel, Pos.CENTER);
	}
	
	protected Game getGame(){
		return game;
	}
	
	protected GameData getData(){
		return data;
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
		new MainMenu(stage, gameFolderPath);
	}
	
	protected Stage getStage(){
		return stage;
	}
	
	public Scene display(){
		Scene scene = new Scene(this, 1000, 600);
		scene.getStylesheets().add(getResources().getString("StylesheetPath"));
		return scene;	
	}
	
}
