package engine.game;

import java.util.ArrayList;
import java.util.List;

import engine.game.gameloop.Screen;
import engine.game.gameloop.StepStrategy;
import engine.game.selectiongroup.ListSG;
import engine.game.selectiongroup.SelectionGroup;
import game_data.Game;

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
	private SelectionGroup<Level> levelsInInitialState;  //zero-indexed
	private List<Integer> unlockedLevelNumbers; //one-indexed
	private int currentLevel; // one-indexed
	private Game game;
	private Screen currentScreen;
	private StepStrategy currentStepStrategy;
	private boolean levelSelectionScreenMode;

	public LevelManager(Game game, StepStrategy currentStepStrategy) {
		levels = new ListSG<>();
		levelsInInitialState = new ListSG<>();
		unlockedLevelNumbers = new ArrayList<>();
		currentLevel = 1;
		this.game = game;
		this.currentStepStrategy = currentStepStrategy;
		this.levelSelectionScreenMode = true;
	}
	
	//TODO Call from GAE with small checkbox, or similar
	public boolean getLevelSelectionScreenMode(){
		return levelSelectionScreenMode;
	}
	
	public void setLevelSelectionScreenMode(boolean levelSelectionScreenMode){
		this.levelSelectionScreenMode = levelSelectionScreenMode;
	}
	
	public Screen getCurrentScreen() {
		return currentScreen;
	}

	public void setCurrentScreen(Screen currentScreen) {
		this.currentScreen = currentScreen;
	}

	public StepStrategy getCurrentStepStrategy() {
		return currentStepStrategy;
	}

	public void setCurrentStepStrategy(StepStrategy currentStepStrategy) {
		this.currentStepStrategy = currentStepStrategy;
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
		if (levelNumberInGame(currentLevel)) {
			this.currentLevel = currentLevel;
		}
		return levelNumberInGame(currentLevel);
	}

	public boolean levelNumberInGame(int queriedLevel) {
		return currentLevel >= 1 && currentLevel <= levels.size();
	}

	public int getLevelNumber() {
		return currentLevel;
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
	 * Since never save levels' state during gameplay, can call this method at
	 * any point during game loop to get levels' initial states.
	 * 
	 * @param filename
	 */

	// Call once at beginning of the game
	public void loadAllSavedLevels() {
		// levels.removeAll();
		levelsInInitialState.addAll(game.cloneLevels());
		levels.addAll(game.getLevels());
	}

	// Call when start up a level (first time AND after die)
	public void resetCurrentLevel() {
		levels.set(currentLevel - 1, game.cloneLevel(levelsInInitialState.get(currentLevel - 1)));
	}

	public SelectionGroup<Level> getLevels() {
		return levels;
	}

	public void addUnlockedLevel(int currentLevel) {
		if(levelNumberInGame(currentLevel)){
			unlockedLevelNumbers.add(currentLevel);
		}
	}
	
	public void clearUnlockedLevels(){
		 unlockedLevelNumbers.removeAll(unlockedLevelNumbers);
	}
	
	public List<Integer> getUnlockedLevelNumbers() {
		return unlockedLevelNumbers;
	}
}