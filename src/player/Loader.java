package player;

import java.io.File;

import game_data.Game;
import game_data.GameData;
import javafx.collections.ObservableList;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Loader {
	private String gamePath;
	private Game game;
	private GameData data;
	private MediaPlayer media;
	
	public Loader(String gameFolderPath){
		gamePath = gameFolderPath;
		setup();
	}
	
	private void setup(){
		data = new GameData();
		game = data.loadGame(gamePath);
		String path = game.getSongPath();
		String uriString = new File(path).toURI().toString();
		media = new MediaPlayer(new Media(uriString));
	}
	
	public String getGamePath(){
		return gamePath;
	}
	
	protected MediaPlayer getMediaPlayer(){
		return media;
	}
	
	protected Game loadGame(){
		return game;
	}
	
	protected GameData loadData(){
		return data;
	}
	
	protected String getSongPath(){
		return game.getSongPath();
	}
	
	protected void saveGame(){
		
	}
	
	protected ObservableList<Score> getScores(){
		return game.getScores();
	}

}
