package player;

import java.io.File;

import data.Game;
import data.GameData;
import javafx.collections.ObservableList;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * This class encapsulates the behavior of playing game music and saving progress in the middle of
 * a game.
 * 
 * @author Jesse
 * @author Jay Doherty
 * 
 */
public class MediaManager {

	private String gameFolderPath;
	private MediaPlayer songPlayer;
	private ObservableList<String> saveStates;
	private int count = 0;

	public MediaManager(Game game, String gamePath) {
		this.gameFolderPath = gamePath;
		this.saveStates = game.getSaves();
		if (!game.getSongPath().equals("")) {
			String uriString = new File(game.getSongPath()).toURI().toString();
			songPlayer = new MediaPlayer(new Media(uriString));
		}
	}
	
	public void setSaves(ObservableList<String> saves){
		saveStates = saves;
	}
	
	public ObservableList<String> getSaves(){
		return saveStates;
	}

	public String getGamePath() {
		return gameFolderPath;
	}

	public MediaPlayer getMediaPlayer() {
		return songPlayer;
	}

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
		StringBuilder saveName = new StringBuilder(game.getName());
		createSaveName(saveName);
		GameData saver = new GameData();
		saver.saveGameState(game, gameFolderPath, saveName.toString());
		saveStates.add(saveName.toString());
	}
	
	private void createSaveName(StringBuilder saveName){
		saveName.append("_");
		saveName.append("save");
		saveName.append("_");
		saveName.append(count);
		saveName.append(".xml");
	}
}
