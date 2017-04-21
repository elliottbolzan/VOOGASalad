package player;

import java.io.File;

import game_data.Game;
import game_data.GameData;
import javafx.collections.ObservableList;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import player.score.Score;

public class MediaManager {
	private String gameFolderPath;
	
	private MediaPlayer songPlayer;
	private ObservableList<String> saveStates;
	private int count = 0;
	
	public MediaManager(String gameFolderPath, ObservableList<String> saveStates) {
		this.gameFolderPath = gameFolderPath;
		
		GameData data = new GameData();
		Game game = data.loadGame(gameFolderPath);
		
		this.saveStates = saveStates;
		
		if (!game.getSongPath().equals("")) {
			String uriString = new File(game.getSongPath()).toURI().toString();
			songPlayer = new MediaPlayer(new Media(uriString));
		}
	}

	public String getGamePath() {
		return gameFolderPath;
	}
	
	public MediaPlayer getMediaPlayer() {
		return songPlayer;
	}

//	protected String getSongPath() {
//		return game.getSongPath();
//	}
//
//	public ObservableList<Score> getScores() {
//		return game.getScores();
//	}
	
	public void playSong() {
		if(songPlayer != null) {
			songPlayer.setCycleCount(MediaPlayer.INDEFINITE);
			songPlayer.play();
		}
	}
	
	public void pauseSong() {
		if(songPlayer != null) {
			songPlayer.pause();
		}
	}
	
	public void saveGame(Game game) {
		count++;
		String saveName = "save" + "_" + count + ".xml";
		GameData saver = new GameData();
		saver.saveGameState(game, gameFolderPath, saveName);
		saveStates.add(saveName);
	}
}
