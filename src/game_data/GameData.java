package game_data;

public class GameData
{

	public void saveGame(Game game, String folderPath)
	{
		GameSaver gs = new GameSaver();
		gs.saveGame(game, folderPath);
	}

	public Game loadGame(String folderPath)
	{
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
