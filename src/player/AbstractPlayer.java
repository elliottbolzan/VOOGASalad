package player;

import java.util.ResourceBundle;

import engine.game.gameloop.GameLoop;
import game_data.Game;
import game_data.GameData;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public abstract class AbstractPlayer extends BorderPane{
	
	private ResourceBundle resources = ResourceBundle.getBundle("resources/Player");
	private String stylesheetPath = resources.getString("StylesheetPath");
	private Game game;
	private GameLoop gameLoop;
	private GameData gameData;
	private Stage stage;
	private Scene scene;
	private String path;
	
	public AbstractPlayer(Game game, String dataFolderPath){
		this.game = game;
		path = dataFolderPath;
		
		buildStage();
		buildGameView();
	}
	
	private void buildStage() {
		stage = new Stage();
		stage.setTitle(resources.getString("PlayerTitle"));
		stage.setMinWidth(600);
		stage.setMinHeight(600);
		stage.setOnCloseRequest(e -> this.exit());

		scene = new Scene(this, 600, 600);
		scene.getStylesheets().add(stylesheetPath);

		stage.setScene(scene);
		stage.show();
	}
	
	private void buildGameView() {
		if(!path.equals("")){
			gameLoop = new GameLoop(scene, game);
			this.setCenter(gameLoop.getGameView());
		}
		
	}
	
	private void exit() {
		if(!path.equals("")){
			gameLoop.pauseTimeline();
		}
		stage.close();
	}

}
