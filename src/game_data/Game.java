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
	 * Constructor for game to initialize name, levels, defaults, songpath
	 */
	public Game() {
		// Load these from a properties file.
		name = "Game";
		levels = new ArrayList<Level>();
		defaults = new ArrayList<Entity>();
		songPath = "";
	}
	
	/**
	 * Getter for game name
	 * @return
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Setter for game name
	 * @param name
	 * 				name to be set to game name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Getter for the list of levels
	 * @return
	 */
	public List<Level> getLevels() {
		return levels;
	}
	
	/**
	 * Setter for list of levels
	 * @param levels
	 * 				levels to be set to game levels
	 */
	public void setLevels(List<Level> levels) {
		this.levels = levels;
	}

	/**
	 * Getter for defaults
	 * @return
	 */
	public List<Entity> getDefaults() {
		return defaults;
	}
	
	/**
	 * Setter for defaults
	 * @param defaults
	 * 				defaults to be set to game defaults
	 */
	public void setDefaults(List<Entity> defaults) {
		this.defaults = defaults;
	}

	/**
	 * Getter for song path
	 * @return
	 */
	public String getSongPath() {
		return songPath;
	}
	
	/**
	 * Setter for songpath
	 * @param songPath
	 * 				songpath to be set to game songpath
	 */
	public void setSongPath(String songPath) {
		this.songPath = songPath;
	}

}
