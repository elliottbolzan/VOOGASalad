package game_data;

import java.util.ArrayList;
import java.util.List;

import engine.Entity;
import engine.entities.CharacterEntity;
import engine.game.Level;

public class GameLoaderTest {

	
	
	public static void main(String[] args) {
		try{
			
		String filepath="data/EntityTesting";
		GameSaver gs = new GameSaver();
		Entity mario = new CharacterEntity();
		mario.setImagePath("resources/images/mario.png");
		mario.setX(100);
		mario.setY(55);
		
		Entity luigi = new CharacterEntity();
		luigi.setImagePath("resources/images/luigi.png");
		luigi.setX(100);
		luigi.setY(55);
		
		Level level = new Level();
		level.addEntity(mario);
		level.addEntity(luigi);
		
		List<Level> levels= new ArrayList<Level>();
		
		gs.saveGame(levels, filepath);
			
			
		GameLoader gl = new GameLoader();
		
		gl.loadGame(filepath);
		}catch (NotAGameFolderException i){
			i.printStackTrace();
		}
		
		}
	
	
	
}
