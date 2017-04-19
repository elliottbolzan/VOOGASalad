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
		if(!path.equals("")){
			String uriString = new File(path).toURI().toString();
			media = new MediaPlayer(new Media(uriString));
		}else{
			media = null;
		}
		
	}
	
	public String getGamePath(){
		return gamePath;
	}
	
	public MediaPlayer getMediaPlayer(){
		return media;
	}
	
	protected Game loadGame(){
		return game;
	}
	
	protected void setGame(Game game){
		this.game = game;
	}
	
	protected GameData loadData(){
		return data;
	}
	
	protected void setData(GameData data){
		this.data = data;
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
