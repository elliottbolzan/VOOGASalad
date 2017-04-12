/**
 * 
 */
package game_data;

import java.util.ArrayList;
import java.util.List;

import engine.Entity;
import engine.game.Level;

/**
 * @author Elliott Bolzan
 *
 */
public class Game {
	
	private String name;
	private List<Level> levels;
	private List<Entity> defaults;
	private String songPath;

	/**
	 * 
	 */
	public Game() {
		// Load these from a properties file.
		name = "Game";
		levels = new ArrayList<Level>();
		defaults = new ArrayList<Entity>();
		songPath = "";
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public List<Level> getLevels() {
		return levels;
	}

	public void setLevels(List<Level> levels) {
		this.levels = levels;
	}

	public List<Entity> getDefaults() {
		return defaults;
	}

	public void setDefaults(List<Entity> defaults) {
		this.defaults = defaults;
	}

	public String getSongPath() {
		return songPath;
	}

	public void setSongPath(String songPath) {
		this.songPath = songPath;
	}

}
