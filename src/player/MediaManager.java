package player;

import java.io.File;

import game_data.Game;
import game_data.GameData;
import javafx.collections.ObservableList;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class MediaManager {

	private String gamePath;
	private Game game;
	private MediaPlayer songPlayer;
	private ObservableList<String> saveStates;
	private int count = 0;

	public MediaManager(Game game, String gamePath, ObservableList<String> saveStates) {
		this.gamePath = gamePath;
		this.game = game;
		this.saveStates = saveStates;
		if (!game.getSongPath().equals("")) {
			String uriString = new File(game.getSongPath()).toURI().toString();
			songPlayer = new MediaPlayer(new Media(uriString));
		}
	}

	public String getGamePath() {
		return gamePath;
	}

	public MediaPlayer getMediaPlayer() {
		return songPlayer;
	}

	// protected String getSongPath() {
	// return game.getSongPath();
	// }
	//
	// public ObservableList<Score> getScores() {
	// return game.getScores();
	// }

	public void playSong() {
		if (songPlayer != null) {
			songPlayer.setCycleCount(MediaPlayer.INDEFINITE);
			songPlayer.play();
		}
	}

	public void pauseSong() {
		if (songPlayer != null) {
			songPlayer.pause();
		}
	}

	public void saveGame(Game game) {
		count++;
		String saveName = "save" + "_" + count + ".xml";
		GameData saver = new GameData();
		saver.saveGameState(game, gamePath, saveName);
		saveStates.add(saveName);
	}
}
