package engine.game;

/**
 * Holds information relating to the current player, or player(s) for a
 * multiplayer game. (Note: This class should NOT be confused with the "Hero" or
 * "Main Character" Entity class.) The code for calling the
 * networking/multiplayer utility should go here.
 * 
 * @author Matthew Barbano
 *
 */
public interface PlayerManager {
	/**
	 * Sets up the game network for multiplayer games using the networking
	 * utility.
	 */
	public void setupNetwork();
}
