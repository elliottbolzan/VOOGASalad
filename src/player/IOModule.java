package player;

import game_data.Game;
import game_data.GameData;

public class IOModule {
	private String gamePath;
	private Game game;
	private GameData data;
	
	public IOModule(String gameFolderPath){
		gamePath = gameFolderPath;
		setup();
	}
	
	private void setup(){
		data = new GameData();
		game = data.loadGame(gamePath);
	}
	
	public Game loadGame(){
		return game;
	}
	
	public GameData loadData(){
		return data;
	}
	
	public void saveGame(){
		
	}

}
