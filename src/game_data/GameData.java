package game_data;

import exceptions.NotAGameFolderException;

public class GameData {
	
	/**
	 * Public method that is called to save the game when the save game button is pressed.
	 * @param game
	 *			game to be saved
	 * @param folderPath
	 * 			folder path to save the game to
	 */
	public void saveGame(Game game, String folderPath) {
		GameSaver gs = new GameSaver();
		gs.saveGame(game, folderPath);
	}
	
	public void saveGameState(Game game, String folderPath, String id){
		
	}
	
	/**
	 * Public method that is called to load the game when the load game button is pressed
	 * @param folderPath
	 * 			folder path where the game wants to be loaded
	 * @return
	 */
	public Game loadGame(String folderPath) {
		GameLoader gl = new GameLoader();
		Game game = null;
		try {
			game = gl.loadGame(folderPath);
		} catch (NotAGameFolderException i) {
			// i.printStackTrace();
		}
		return game;
	}
}