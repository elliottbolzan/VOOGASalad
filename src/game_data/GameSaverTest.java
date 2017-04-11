package game_data;

import java.util.ArrayList;
import java.util.List;

import engine.Entity;
import engine.entities.CharacterEntity;
import engine.game.Level;

public class GameSaverTest {

	
	
	public static void main(String[] args) {

		GameSaver gs = new GameSaver();

		// Entity entity = new
		// CharacterEntity("Guilherme","src/resources/images/mario.png");
		Entity entity = new CharacterEntity();
		entity.setName("Guilherme");
		entity.setImagePath("src/resources/images/mario.png");

		entity.setX(100);
		entity.setY(55);

		// Entity luigi = new
		// CharacterEntity("Luigi","src/resources/images/luigi.png");
		Entity luigi = new CharacterEntity();
		luigi.setName("Luigi");
		luigi.setImagePath("src/resources/images/luigi.png");

		luigi.setX(100);
		luigi.setY(65);

		// C:/Users/Michael8417/workspace/voogasalad_duwaldorf/
		String filepath = "data/SaveTesting";

		Level l = new Level();
		l.addEntity(entity);
		l.addEntity(luigi);

		List<Level> levels = new ArrayList<Level>();
		levels.add(l);
		
		
		Game g  = new Game();
		g.setName("Testing");
		g.setLevels(levels);
		
		gs.saveGame(g, filepath);
	}
}
