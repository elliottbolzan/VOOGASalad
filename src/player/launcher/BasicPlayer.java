package player.launcher;

import java.util.ResourceBundle;

import game_data.Game;
import javafx.stage.Stage;
import player.MediaManager;
import polyglot.Polyglot;

public class BasicPlayer extends AbstractPlayer {

	public BasicPlayer(Stage primaryStage, Game game, MediaManager mediaManager, Polyglot polyglot, ResourceBundle IOResources) {
		super(primaryStage, game, mediaManager, polyglot, IOResources);
		
		this.getRunningGameLoop().startTimeline();
	}

}