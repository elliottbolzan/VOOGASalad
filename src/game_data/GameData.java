package game_data;

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
	
	/**
	 * Saves the game to the chosen name
	 * @param game
	 * @param folderPath
	 * @param saveName
	 */
	public void saveGameState(Game game, String folderPath, String saveName){
		GameSaver gs = new GameSaver();
		gs.saveGameState(game, folderPath, saveName);
	}
	
	/**
	 * Public method that is called to load the game when the load game button is pressed
	 * @param folderPath
	 * 			folder path where the game wants to be loaded
	 * @return
	 */
	public Game loadGame(String folderPath) throws Exception {
		GameLoader gl = new GameLoader();
		return gl.loadGame(folderPath, "settings.xml");
	}
	
	/**
	 * Load a save file
	 * @param folderPath
	 * @param saveName
	 * @return
	 */
	public Game loadGameState(String folderPath, String saveName) throws Exception {
		GameLoader gl = new GameLoader();
		return gl.loadGame(folderPath, saveName);
	}

}