package engine.game;

import engine.game.selectiongroup.ListSG;
import engine.game.selectiongroup.SelectionGroup;
import exceptions.NotAGameFolderException;
import game_data.Game;
import game_data.GameData;

/**
 * Holds all the levels in the current game and allows for game-wide behavior.
 * 
 * IMPORTANT NOTE TO ANYONE USING THIS CLASS: When using the methods in this
 * class, levels are one-indexed (likely the only information anyone else will
 * need to know). If for some reason anyone needs to use SelectionGroup, that is
 * zero-indexed.
 * 
 * @author Matthew Barbano
 *
 */
public class LevelManager {
	private SelectionGroup<Level> levels; // zero-indexed
	private int currentLevel; // one-indexed
	private Game game;

	public LevelManager(Game game) {
		levels = new ListSG<>(); // TODO: Change to reflection, or something
									// more modular

		currentLevel = 1;
		this.game = game;
	}

	/**
	 * External Engine API. Needed for authoring.
	 * 
	 * @param level
	 * @return
	 */
	public void addLevel(Level level) {
		levels.add(level);
	}

	/**
	 * External Engine API. Needed for gameplay.
	 * 
	 * @return
	 */
	public Level getCurrentLevel() {
		return levels.get(currentLevel - 1);
	}

	/**
	 * Returns true if in range and successfully set level. Otherwise, returns
	 * false and currentLevel remains unchanged.
	 * 
	 * @param currentLevel
	 * @return
	 */

	public boolean setLevelNumber(int currentLevel) {
		boolean inRange = currentLevel >= 1 && currentLevel <= levels.size();
		if (inRange) {
			this.currentLevel = currentLevel;
		}
		return inRange;
	}

	public int getLevelNumber() {
		return currentLevel;
	}

	/**
	 * External Engine API. Called by the Game Player during gameplay. Pulls up
	 * the pause menu.
	 * 
	 * @return
	 */
	public void pause() {

	}

	/**
	 * External Engine API. Called by the GAE during editing and uses methods
	 * from Game Data.
	 * 
	 * @return
	 */
	public void saveCurrentLevel() {

	}

	/**
	 * Called only from GAE. (Maybe don't need this method?). Once game play
	 * phase begins, level state should never be saved (unless add checkpoints).
	 * Only Level PROGRESS (i.e. on the level selection screen) should be saved.
	 */
	public void saveAllLevels() {
		// GameDataExternalAPI gameData = new GameDataExternalAPI();
		// gameData.saveGame(levels); // TODO Ask Game Data people if they can
		// save
		// the entire SelectionGroup object (so I
		// don't have to reconstruct a graph from a
		// List...alternatively if I have them save
		// the edge list, this will be OK: create
		// getSaveableList() method in
		// SelectionGroup interface)

		System.out.println("Saved game");
	}

	/**
	 * External Engine API. Called by the GAE during editing and uses methods
	 * from Game Data.
	 * 
	 * @param filename
	 * @return
	 */
	public void openLevel(String filename) {

	}

	/**
	 * Since never save levels' state during gameplay, can call this method at
	 * any point during game loop to get levels' initial states.
	 * 
	 * @param filename
	 */

	public void loadAllSavedLevels() {
		levels.removeAll();
		levels.addAll(game.getLevels()); // TODO
																	// uncomment
																	// once
																	// GameData
																	// is added
		/*
		 * levels.add(new Level()); //TODO: remove this after testing
		 * levels.add(new Level()); levels.add(new Level());
		 * System.out.println("The Levels SelectionGroup has List: " +
		 * levels.getListRepresentation());
		 * System.out.println("Loaded the current level");
		 */
	}

	/*
	 * public void startCurrentLevel() { getCurrentLevel().start(); }
	 */

	public SelectionGroup<Level> getLevels() {
		return levels;
	}

}