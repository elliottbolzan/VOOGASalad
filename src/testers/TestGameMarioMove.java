package testers;

import engine.game.gameloop.GameLoop;
import game_data.Game;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * note to self: use case 2; ensure use case 1 still works! (should still print "level initialized", and "saved/loaded game"
 * @author Matthew Barbano
 *
 */
public class TestGameMarioMove extends Application{
	public static void main(String[] args){
		launch(args);
	}
	public void start(Stage stage) throws Exception{
		//The Game Player must give me all parameters to the GameLoop constructor, since GameLoop is instantiated in it
		Game game = new Game();
		Scene scene = new Scene(new Group(), 250, 250, Color.BLUE);
		GameLoop gameLoop = new GameLoop(scene, game);
		gameLoop.startTimeline();
	}

}
