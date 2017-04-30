package usecases;

import data.GameDataExternalAPI;
import engine.game.Level;
import engine.game.LevelManager;

/*
 *	This is an implementation of the use case for saving a game into an XML file
 */
public class LoadLevel {
	
	//The string will point to a file that contains the Game Data
	public LoadLevel(String filepath, GameDataExternalAPI game, LevelManager lm){
		//The loadGame method will handle the creation of the levels 
		Level newLevel = game.loadGame(filepath);
		//After the level is created, we can hand it to the GameEngine/GameAuthoring environment to manipulate
		//For example, we can add the level to the level manager
		lm.addLevel((Level) newLevel);
		//The level is now set with all Entities and can be manipulated by the user
	}
}
