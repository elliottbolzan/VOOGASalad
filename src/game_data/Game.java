package game_data;

import java.util.ArrayList;
import java.util.List;
import engine.Entity;
import engine.entities.CameraEntity;
import engine.game.Level;
import player.Score;

/**
 * @author Elliott Bolzan
 * 
 *         This class represents a Game. It is designed to be shared through
 *         submodules: the GameData, Game Authoring Environment, Game Player and
 *         Game Engine all can make use of this class. It encapsulates levels,
 *         settings, and default Entities.
 */
public class Game {
	private String name;
	private List<Level> levels;
	private List<Entity> defaults;
	private String songPath;
	private String backPath;
	private String info;
	private String achievements;
	private CameraEntity camera;
	private List<Score> scores;
	
	/**
	 * Returns an empty game object, with default values pre-loaded.
	 */
	public Game() {
		// Load these from a properties file.
		name = "Game";
		levels = new ArrayList<Level>();
		defaults = new ArrayList<Entity>();
		songPath = "";
		setBackPath("");
		setInfo("");
		setAchievements("");
		camera = new CameraEntity();
		scores = new ArrayList<>();
	}
	
	/**
	 * @return the game's name.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Set the game's name.
	 * @param name the new name for the game.
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @return the game's levels.
	 */
	public List<Level> getLevels() {
		return levels;
	}
	
	/**
	 * Set the game's levels.
	 * @param levels the new levels for the game.
	 */
	public void setLevels(List<Level> levels) {
		this.levels = levels;
	}
	
	/**
	 * @return the game's default Entities.
	 */
	public List<Entity> getDefaults() {
		return defaults;
	}
	
	/**
	 * Set the game's default Entities.
	 * @param defaults the new default Entities.
	 */
	public void setDefaults(List<Entity> defaults) {
		this.defaults = defaults;
	}
	
	/**
	 * @return the path to the game's song.
	 */
	public String getSongPath() {
		return songPath;
	}
	
	/**
	 * Set the game's song path.
	 * @param songPath the new song path for the game.
	 */
	public void setSongPath(String songPath) {
		this.songPath = songPath;
	}
	
	public String getBackPath() {
		return backPath;
	}
	public void setBackPath(String backPath) {
		this.backPath = backPath;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getAchievements() {
		return achievements;
	}
	public void setAchievements(String achievements) {
		this.achievements = achievements;
	}
	
	/**
	 * @return the game's camera.
	 */
	public CameraEntity getCamera() {
		return camera;
	}
	
	/**
	 * Set the game's camera.
	 * @param camera : the new camera for the game
	 */
	public void setCamera(CameraEntity camera) {
		this.camera = camera;
	}
	
	/**
	 * Add a new highscore
	 * @param score the score when game ended
	 * @param time  the time remaining when game ended
	 */
	public void setScore(String score, String time){
		if(scores.size() < 10){
			Score highscore = new Score(score, time);
			scores.add(highscore);
		}		
	}
	
	/**
	 * 
	 * @return the list of highscores
	 */
	public List<Score> getScores(){
		return scores;
	}
	
	
}