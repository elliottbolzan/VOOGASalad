package game_data;

public class GameData {

	public void saveGame(Game game, String folderpath) {
		GameSaver gs = new GameSaver();
		gs.saveGame(game, folderpath);
	}

	public Game loadGame(String folderpath) {
		GameLoader gl = new GameLoader();
		Game game = new Game();
		try {
			game.setLevels(gl.loadGame(folderpath));
		} catch (NotAGameFolderException i) {
			i.printStackTrace();
		}
		return game;
	}
}
