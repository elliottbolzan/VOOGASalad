package player;

import java.io.File;

import game_data.Game;
import game_data.GameData;
import javafx.collections.ObservableList;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import player.score.Score;

public class Loader {
	private String gameFolderPath;
	private Game game;
	
	private MediaPlayer songPlayer;
	private ObservableList<String> saveStates;
	private int count = 0;
	
	public Loader(String gameFolderPath, ObservableList<String> saveStates) {
		this.gameFolderPath = gameFolderPath;
		
		GameData data = new GameData();
		this.game = data.loadGame(gameFolderPath);
		
		this.saveStates = saveStates;
		
		if (!this.getGame().getSongPath().equals("")) {
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

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	protected String getSongPath() {
		return game.getSongPath();
	}

	public ObservableList<Score> getScores() {
		return game.getScores();
	}
	
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
	
	public void saveGame() {
		count++;
		String saveName = "save" + "_" + count + ".xml";
		GameData saver = new GameData();
		saver.saveGameState(this.getGame(), gameFolderPath, saveName);
		saveStates.add(saveName);
	}
}
