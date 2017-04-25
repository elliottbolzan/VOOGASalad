package testers;

import engine.game.Level;
import engine.game.LevelManager;
import engine.game.gameloop.GameLoop;
import game_data.Game;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import player.score.Overlay;

/**
 * Note to self: Use case 1.
 * @author Matthew Barbano
 *
 */
public class TestGameEngineStartup extends Application{
	public static void main(String[] args){
		launch(args);
	}
	public void start(Stage stage) throws Exception{
		//Editing phase (the following code is in the GAE)
		Game game = new Game();
//		LevelManager levelManager = new LevelManager(game);
//		Level level1 = new Level();
		//Add Entities here
//		levelManager.addLevel(level1);
//		levelManager.saveAllLevels();
		//----------------------PRETEND ARE IN ENTIRELY SEPARATE MAIN METHODS---------------
		//Playing phase (the following code is in the Game Player)
		Scene scene = new Scene(new Group(), 250, 250, Color.BLUE);
//		GameLoop gameLoop = new GameLoop(scene, game);
//		gameLoop.startTimeline();
	}
}
