package player.launcher;

import java.util.ResourceBundle;

import game_data.Game;
import javafx.stage.Stage;
import polyglot.Polyglot;

public class BasicPlayer extends AbstractPlayer {

	public BasicPlayer(Stage primaryStage, Game game, Polyglot polyglot, ResourceBundle IOResources, boolean firstTimeLoading) {
		super(primaryStage, game, polyglot, IOResources, firstTimeLoading);
		
		this.getRunningGameLoop().startTimeline();
	}

}